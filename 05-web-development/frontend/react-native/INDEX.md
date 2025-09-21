# React Native - Mobilny Development

## Cel
Kompleksowy przewodnik po React Native - framework do tworzenia aplikacji mobilnych cross-platform z użyciem React.

## Problem
Potrzeba tworzenia aplikacji mobilnych, które:
- Działają na iOS i Android
- Wykorzystują natywne komponenty
- Mają wysoką wydajność
- Są łatwe w utrzymaniu
- Współdzielą kod między platformami

## Pojęcia Kluczowe

### React Native Fundamentals
- **Cross-platform** - jedna aplikacja na wiele platform
- **Native components** - natywne komponenty UI
- **Bridge** - komunikacja między JavaScript a natywnym kodem
- **Metro bundler** - bundler dla React Native
- **Hot reload** - szybkie odświeżanie podczas rozwoju

### Architektura
- **Component-based** - architektura oparta na komponentach
- **Unidirectional data flow** - jednokierunkowy przepływ danych
- **Virtual DOM** - wirtualne drzewo DOM
- **Native modules** - natywne moduły i biblioteki
- **Platform-specific code** - kod specyficzny dla platformy

### Kluczowe Biblioteki
- **React Navigation** - system nawigacji
- **Redux/Context** - zarządzanie stanem
- **AsyncStorage** - lokalne przechowywanie danych
- **React Native Paper** - Material Design komponenty
- **Expo** - narzędzia i usługi deweloperskie

## Struktura / Diagram

```
React Native App
├── JavaScript Thread
│   ├── React Components
│   ├── Business Logic
│   └── State Management
├── Bridge
└── Native Thread
    ├── iOS (Objective-C/Swift)
    └── Android (Java/Kotlin)
```

## Przepływ Działania

### 1. Setup i Konfiguracja
- Instalacja React Native CLI
- Konfiguracja środowiska deweloperskiego
- Setup projektu (Expo vs React Native CLI)
- Konfiguracja bundlera (Metro)

### 2. Rozwój Aplikacji
- Tworzenie komponentów
- Implementacja nawigacji
- Zarządzanie stanem
- Integracja z natywnymi funkcjami

### 3. Testowanie i Debugowanie
- Testowanie na symulatorach/emulatorach
- Testowanie na urządzeniach fizycznych
- Debugowanie z React Native Debugger
- Profiling wydajności

### 4. Build i Deployment
- Konfiguracja build'ów
- Generowanie APK/IPA
- Publikacja w sklepach
- OTA updates (Expo)

## Przykłady Użycia

### Podstawowy Komponent
```jsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const WelcomeScreen = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Witaj w React Native!</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
  },
});

export default WelcomeScreen;
```

### Nawigacja
```jsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Profile" component={ProfileScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};
```

### Zarządzanie Stanem
```jsx
import React, { createContext, useContext, useReducer } from 'react';

const AppContext = createContext();

const appReducer = (state, action) => {
  switch (action.type) {
    case 'SET_USER':
      return { ...state, user: action.payload };
    default:
      return state;
  }
};

const AppProvider = ({ children }) => {
  const [state, dispatch] = useReducer(appReducer, { user: null });
  
  return (
    <AppContext.Provider value={{ state, dispatch }}>
      {children}
    </AppContext.Provider>
  );
};

export const useApp = () => useContext(AppContext);
```

## Implementacja (Fragmenty Kodu)

### Custom Hook
```jsx
import { useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

const useAsyncStorage = (key, initialValue) => {
  const [value, setValue] = useState(initialValue);
  
  useEffect(() => {
    loadValue();
  }, [key]);
  
  const loadValue = async () => {
    try {
      const item = await AsyncStorage.getItem(key);
      if (item !== null) {
        setValue(JSON.parse(item));
      }
    } catch (error) {
      console.error('Error loading from storage:', error);
    }
  };
  
  const saveValue = async (newValue) => {
    try {
      setValue(newValue);
      await AsyncStorage.setItem(key, JSON.stringify(newValue));
    } catch (error) {
      console.error('Error saving to storage:', error);
    }
  };
  
  return [value, saveValue];
};
```

### Performance Optimization
```jsx
import React, { memo, useCallback, useMemo } from 'react';
import { FlatList, View, Text } from 'react-native';

const ListItem = memo(({ item, onPress }) => {
  const handlePress = useCallback(() => {
    onPress(item);
  }, [item, onPress]);
  
  return (
    <TouchableOpacity onPress={handlePress}>
      <Text>{item.title}</Text>
    </TouchableOpacity>
  );
});

const OptimizedList = ({ data, onItemPress }) => {
  const keyExtractor = useCallback((item) => item.id.toString(), []);
  
  const renderItem = useCallback(({ item }) => (
    <ListItem item={item} onPress={onItemPress} />
  ), [onItemPress]);
  
  const memoizedData = useMemo(() => data, [data]);
  
  return (
    <FlatList
      data={memoizedData}
      renderItem={renderItem}
      keyExtractor={keyExtractor}
      removeClippedSubviews={true}
      maxToRenderPerBatch={10}
    />
  );
};
```

## Zalety

### Cross-platform
- **Jedna aplikacja** - dla iOS i Android
- **Współdzielony kod** - oszczędność czasu i zasobów
- **Natywna wydajność** - używanie natywnych komponentów
- **Hot reload** - szybki rozwój

### Developer Experience
- **Znajomy ekosystem** - React i JavaScript
- **Bogate narzędzia** - debugging, profiling
- **Duża społeczność** - wsparcie i biblioteki
- **Dokumentacja** - obszerna dokumentacja

## Wady

### Ograniczenia
- **Natywne funkcje** - ograniczony dostęp do API
- **Performance** - może być wolniejszy od natywnych aplikacji
- **Bundle size** - większy rozmiar aplikacji
- **Platform differences** - różnice między iOS i Android

### Złożoność
- **Setup** - skomplikowana konfiguracja
- **Debugging** - trudniejsze debugowanie
- **Dependencies** - zależność od zewnętrznych bibliotek
- **Updates** - aktualizacje mogą być problematyczne

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Potrzebujesz aplikacji cross-platform
- Zespół zna React
- Szybki czas rozwoju
- Proste do średnio złożone aplikacje
- Chcesz współdzielić kod

### Nie używać gdy:
- Bardzo złożone aplikacje natywne
- Krytyczne wymagania wydajnościowe
- Pełny dostęp do natywnych API
- Zespół ma doświadczenie tylko z natywnym rozwojem

## Powiązane Tematy/Wzorce

- [Stacking Theory](./Stacking_Theory.md) - Teoria warstw w React Native
- [React Native Patterns](./React_Native_Patterns.md) - Wzorce i najlepsze praktyki
- [React Fundamentals](../react/INDEX.md) - Podstawy React
- [Mobile Development](../mobile-development/INDEX.md) - Mobilny development
- [Performance Optimization](../performance/INDEX.md) - Optymalizacja wydajności

## Źródła / Dalsza Lektura

- [React Native Documentation](https://reactnative.dev/)
- [Expo Documentation](https://docs.expo.dev/)
- [React Navigation](https://reactnavigation.org/)
- [React Native Paper](https://callstack.github.io/react-native-paper/)
- [React Native Performance](https://reactnative.dev/docs/performance)

---

Ten katalog jest częścią sekcji frontend.

## Zawartość Katalogu

### Główne Przewodniki
- **[Stacking Theory](./Stacking_Theory.md)** - Teoria warstw (stacking) w React Native
- **[React Native Patterns](./React_Native_Patterns.md)** - Wzorce i najlepsze praktyki

### Tematy do Rozszerzenia
- [ ] **Navigation Patterns** - Wzorce nawigacji w React Native
- [ ] **State Management** - Zarządzanie stanem w aplikacjach mobilnych
- [ ] **Performance Optimization** - Optymalizacja wydajności
- [ ] **Testing Strategies** - Strategie testowania aplikacji React Native
- [ ] **Deployment & CI/CD** - Wdrażanie i automatyzacja


