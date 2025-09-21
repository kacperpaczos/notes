# Teoria Warstw (Stacking) w React Native - Przewodnik Praktyczny

## Cel
Kompleksowy przewodnik po teorii warstw (stacking) w React Native - jak elementy są rysowane i nakładane na siebie w aplikacjach mobilnych.

## Problem
Zrozumienie i kontrola nad tym, jak elementy są rysowane i nakładane na siebie w React Native:
- Kolejność rysowania elementów
- Kontrola nad warstwami (zIndex, elevation)
- Interakcja z nawigacją (React Navigation)
- Overlay'y i modały
- Różnice między iOS i Android

## Pojęcia Kluczowe

### Podstawy Stacking
- **Drzewo widoków** - hierarchia komponentów
- **Rodzic-dziecko** - relacje w drzewie komponentów
- **Siblings** - elementy tego samego rodzica
- **Kontekst warstw** - środowisko rysowania elementu

### Właściwości Kontroli Warstw
- **zIndex** - kontrola kolejności rysowania
- **elevation** - cień i warstwa na Androidzie
- **position** - pozycjonowanie (absolute, relative)
- **overflow** - przycinanie zawartości

### Mechanizmy Nawigacji
- **React Navigation** - system nawigacji
- **Header/Drawer/TabBar** - elementy nawigacyjne
- **Portal** - renderowanie poza normalnym drzewem
- **Modal** - natywna warstwa overlay

## Struktura / Diagram

```
React Native App
├── Navigation Container
│   ├── Stack Navigator
│   │   ├── Header (własny kontekst warstw)
│   │   └── Screen Content
│   │       ├── Normal Components
│   │       └── Overlay (ograniczony kontekst)
│   └── Tab Navigator
└── Portal/Modal Layer (najwyższa warstwa)
```

## Przepływ Działania

### 1. Podstawowe Zasady Stacking
```jsx
// Kolejność rysowania bez zIndex
<View>
  <View style={{ backgroundColor: 'red' }} />    {/* Rysowane pierwsze (na dole) */}
  <View style={{ backgroundColor: 'blue' }} />   {/* Rysowane drugie (na górze) */}
  <View style={{ backgroundColor: 'green' }} />  {/* Rysowane trzecie (najwyżej) */}
</View>

// Z zIndex - kontrola kolejności
<View>
  <View style={{ 
    backgroundColor: 'red', 
    zIndex: 3,
    position: 'absolute'
  }} />    {/* Najwyżej */}
  <View style={{ 
    backgroundColor: 'blue', 
    zIndex: 1,
    position: 'absolute'
  }} />    {/* Na dole */}
  <View style={{ 
    backgroundColor: 'green', 
    zIndex: 2,
    position: 'absolute'
  }} />    {/* W środku */}
</View>
```

### 2. Elevation na Androidzie
```jsx
// Android elevation + zIndex
<View style={{
  position: 'absolute',
  top: 0,
  left: 0,
  right: 0,
  backgroundColor: 'white',
  zIndex: 999,           // Kolejność rysowania
  elevation: 10,         // Cień i warstwa na Androidzie
  shadowColor: '#000',   // iOS shadow
  shadowOffset: { width: 0, height: 2 },
  shadowOpacity: 0.25,
  shadowRadius: 3.84,
}}>
  <Text>Overlay z cieniem</Text>
</View>
```

### 3. Interakcja z Nawigacją
```jsx
// ❌ PROBLEM: zIndex w ekranie nie przykryje headera
function Screen() {
  return (
    <View style={{ flex: 1 }}>
      <Text>Treść ekranu</Text>
      
      {/* Ten overlay NIE przykryje headera React Navigation */}
      <View style={{
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        height: 50,
        backgroundColor: 'red',
        zIndex: 9999, // Nie zadziała!
      }}>
        <Text>Overlay w ekranie</Text>
      </View>
    </View>
  );
}

// ✅ ROZWIĄZANIE: Portal przykryje wszystko
import { Portal } from 'react-native-paper';

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={Screen} />
      </Stack.Navigator>
      
      {/* Portal renderuje poza nawigacją */}
      <Portal>
        <View style={{
          position: 'absolute',
          top: 0,
          left: 0,
          right: 0,
          height: 50,
          backgroundColor: 'red',
          zIndex: 9999,
          elevation: 9999,
        }}>
          <Text>Overlay ponad nawigacją</Text>
        </View>
      </Portal>
    </NavigationContainer>
  );
}
```

## Przykłady Użycia

### Overlay w Obrębie Ekranu
```jsx
// Gdy header nie przeszkadza
function Screen() {
  const [showOverlay, setShowOverlay] = useState(false);
  
  return (
    <View style={{ flex: 1 }}>
      {/* Główna treść */}
      <ScrollView>
        <Text>Treść ekranu</Text>
        <Button 
          title="Pokaż overlay" 
          onPress={() => setShowOverlay(true)} 
        />
      </ScrollView>
      
      {/* Overlay w obrębie ekranu */}
      {showOverlay && (
        <View style={{
          position: 'absolute',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(0,0,0,0.5)',
          zIndex: 999,
          elevation: 10,
          justifyContent: 'center',
          alignItems: 'center',
        }}>
          <View style={{
            backgroundColor: 'white',
            padding: 20,
            borderRadius: 10,
            margin: 20,
          }}>
            <Text>To jest overlay w ekranie</Text>
            <Button 
              title="Zamknij" 
              onPress={() => setShowOverlay(false)} 
            />
          </View>
        </View>
      )}
    </View>
  );
}
```

### Overlay Ponad Nawigacją
```jsx
import { Portal } from 'react-native-paper';
import { useSafeAreaInsets } from 'react-native-safe-area-context';

function GlobalOverlay() {
  const insets = useSafeAreaInsets();
  const [showBanner, setShowBanner] = useState(true);
  
  return (
    <Portal>
      {/* Banner na górze ekranu */}
      {showBanner && (
        <View style={{
          position: 'absolute',
          top: insets.top, // Uwzględnij notch/status bar
          left: 0,
          right: 0,
          backgroundColor: '#FF6B6B',
          padding: 10,
          zIndex: 9999,
          elevation: 9999,
          pointerEvents: 'none', // Przepuszczaj dotyk
        }}>
          <Text style={{ color: 'white', textAlign: 'center' }}>
            🚀 Nowa funkcja dostępna!
          </Text>
        </View>
      )}
      
      {/* Floating Action Button */}
      <TouchableOpacity
        style={{
          position: 'absolute',
          bottom: 100,
          right: 20,
          width: 60,
          height: 60,
          borderRadius: 30,
          backgroundColor: '#007AFF',
          justifyContent: 'center',
          alignItems: 'center',
          zIndex: 9999,
          elevation: 9999,
          shadowColor: '#000',
          shadowOffset: { width: 0, height: 2 },
          shadowOpacity: 0.25,
          shadowRadius: 3.84,
        }}
        onPress={() => console.log('FAB pressed')}
      >
        <Text style={{ color: 'white', fontSize: 24 }}>+</Text>
      </TouchableOpacity>
    </Portal>
  );
}
```

### Modal vs Portal
```jsx
import { Modal } from 'react-native';
import { Portal } from 'react-native-paper';

function ModalComparison() {
  const [showRNModal, setShowRNModal] = useState(false);
  const [showPortalModal, setShowPortalModal] = useState(false);
  
  return (
    <View>
      <Button 
        title="React Native Modal" 
        onPress={() => setShowRNModal(true)} 
      />
      <Button 
        title="Portal Modal" 
        onPress={() => setShowPortalModal(true)} 
      />
      
      {/* React Native Modal - natywna warstwa */}
      <Modal
        visible={showRNModal}
        transparent={true}
        animationType="fade"
        onRequestClose={() => setShowRNModal(false)}
      >
        <View style={{
          flex: 1,
          backgroundColor: 'rgba(0,0,0,0.5)',
          justifyContent: 'center',
          alignItems: 'center',
        }}>
          <View style={{
            backgroundColor: 'white',
            padding: 20,
            borderRadius: 10,
            margin: 20,
          }}>
            <Text>React Native Modal</Text>
            <Text>Przykrywa WSZYSTKO (nawet system UI)</Text>
            <Button 
              title="Zamknij" 
              onPress={() => setShowRNModal(false)} 
            />
          </View>
        </View>
      </Modal>
      
      {/* Portal Modal - w kontekście aplikacji */}
      <Portal>
        {showPortalModal && (
          <View style={{
            position: 'absolute',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0,0,0,0.5)',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 9999,
            elevation: 9999,
          }}>
            <View style={{
              backgroundColor: 'white',
              padding: 20,
              borderRadius: 10,
              margin: 20,
            }}>
              <Text>Portal Modal</Text>
              <Text>Przykrywa nawigację, ale nie system UI</Text>
              <Button 
                title="Zamknij" 
                onPress={() => setShowPortalModal(false)} 
              />
            </View>
          </View>
        )}
      </Portal>
    </View>
  );
}
```

## Implementacja (Fragmenty Kodu)

### Hook do Zarządzania Overlay'ami
```jsx
import { useState, useEffect } from 'react';
import { Portal } from 'react-native-paper';
import { useSafeAreaInsets } from 'react-native-safe-area-context';

function useOverlay() {
  const [overlays, setOverlays] = useState([]);
  const insets = useSafeAreaInsets();
  
  const showOverlay = (id, component, options = {}) => {
    const overlay = {
      id,
      component,
      options: {
        zIndex: 9999,
        elevation: 9999,
        pointerEvents: 'auto',
        ...options
      }
    };
    
    setOverlays(prev => [...prev.filter(o => o.id !== id), overlay]);
  };
  
  const hideOverlay = (id) => {
    setOverlays(prev => prev.filter(o => o.id !== id));
  };
  
  const hideAllOverlays = () => {
    setOverlays([]);
  };
  
  const OverlayContainer = () => (
    <Portal>
      {overlays.map(overlay => (
        <View
          key={overlay.id}
          style={{
            position: 'absolute',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            zIndex: overlay.options.zIndex,
            elevation: overlay.options.elevation,
            pointerEvents: overlay.options.pointerEvents,
          }}
        >
          {overlay.component}
        </View>
      ))}
    </Portal>
  );
  
  return {
    showOverlay,
    hideOverlay,
    hideAllOverlays,
    OverlayContainer,
    insets
  };
}

// Użycie
function MyApp() {
  const { showOverlay, hideOverlay, OverlayContainer } = useOverlay();
  
  const showNotification = () => {
    showOverlay('notification', (
      <View style={{
        backgroundColor: '#4CAF50',
        padding: 15,
        margin: 20,
        borderRadius: 8,
        flexDirection: 'row',
        alignItems: 'center',
      }}>
        <Text style={{ color: 'white', flex: 1 }}>
          ✅ Operacja zakończona pomyślnie
        </Text>
        <TouchableOpacity onPress={() => hideOverlay('notification')}>
          <Text style={{ color: 'white', fontWeight: 'bold' }}>✕</Text>
        </TouchableOpacity>
      </View>
    ), {
      pointerEvents: 'none',
      top: 100, // Custom positioning
    });
  };
  
  return (
    <View style={{ flex: 1 }}>
      <Button title="Pokaż powiadomienie" onPress={showNotification} />
      <OverlayContainer />
    </View>
  );
}
```

### Komponent Banner z Auto-hide
```jsx
import { useEffect, useRef } from 'react';
import { Animated } from 'react-native';

function DevBanner() {
  const slideAnim = useRef(new Animated.Value(-100)).current;
  const opacityAnim = useRef(new Animated.Value(0)).current;
  
  useEffect(() => {
    // Slide in animation
    Animated.parallel([
      Animated.timing(slideAnim, {
        toValue: 0,
        duration: 300,
        useNativeDriver: true,
      }),
      Animated.timing(opacityAnim, {
        toValue: 1,
        duration: 300,
        useNativeDriver: true,
      }),
    ]).start();
    
    // Auto-hide after 5 seconds
    const timer = setTimeout(() => {
      Animated.parallel([
        Animated.timing(slideAnim, {
          toValue: -100,
          duration: 300,
          useNativeDriver: true,
        }),
        Animated.timing(opacityAnim, {
          toValue: 0,
          duration: 300,
          useNativeDriver: true,
        }),
      ]).start();
    }, 5000);
    
    return () => clearTimeout(timer);
  }, []);
  
  return (
    <Animated.View style={{
      position: 'absolute',
      top: 0,
      left: 0,
      right: 0,
      backgroundColor: '#FF6B6B',
      padding: 10,
      paddingTop: 50, // Safe area
      zIndex: 9999,
      elevation: 9999,
      pointerEvents: 'none',
      transform: [{ translateY: slideAnim }],
      opacity: opacityAnim,
    }}>
      <Text style={{ 
        color: 'white', 
        textAlign: 'center',
        fontWeight: 'bold',
      }}>
        🚧 Tryb deweloperski
      </Text>
    </Animated.View>
  );
}
```

### Safe Area Overlay
```jsx
import { useSafeAreaInsets } from 'react-native-safe-area-context';

function SafeOverlay({ children, style = {}, ...props }) {
  const insets = useSafeAreaInsets();
  
  return (
    <View style={[
      {
        position: 'absolute',
        top: insets.top,
        left: insets.left,
        right: insets.right,
        bottom: insets.bottom,
      },
      style
    ]} {...props}>
      {children}
    </View>
  );
}

// Użycie
function App() {
  return (
    <NavigationContainer>
      {/* Główna aplikacja */}
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
      </Stack.Navigator>
      
      {/* Overlay z uwzględnieniem safe area */}
      <Portal>
        <SafeOverlay
          style={{
            zIndex: 9999,
            elevation: 9999,
            pointerEvents: 'none',
          }}
        >
          <DevBanner />
        </SafeOverlay>
      </Portal>
    </NavigationContainer>
  );
}
```

## Zalety

### Kontrola nad UI
- **Precyzyjna kontrola** - dokładne pozycjonowanie elementów
- **Przewidywalność** - jasne zasady rysowania
- **Elastyczność** - różne strategie dla różnych potrzeb
- **Konsystencja** - spójne zachowanie na iOS i Android

### UX/UI
- **Overlay'y** - informacyjne banery i powiadomienia
- **Modały** - okna dialogowe i formularze
- **Floating elements** - przyciski i wskaźniki
- **Safe area** - uwzględnienie notch i system UI

## Wady

### Złożoność
- **Debugowanie** - trudniejsze śledzenie problemów z warstwami
- **Performance** - nadużywanie zIndex może wpływać na wydajność
- **Maintenance** - więcej kodu do utrzymania
- **Testing** - trudniejsze testowanie overlay'ów

### Problemy
- **Memory leaks** - Portal może powodować wycieki pamięci
- **Navigation conflicts** - konflikty z nawigacją
- **Platform differences** - różnice między iOS i Android
- **Accessibility** - problemy z dostępnością overlay'ów

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Potrzebujesz overlay'ów ponad nawigacją
- Tworzysz floating action buttons
- Implementujesz system powiadomień
- Potrzebujesz modali i dialogów
- Chcesz banery informacyjne

### Nie używać gdy:
- Proste aplikacje bez overlay'ów
- Ograniczona pamięć
- Problemy z wydajnością
- Zespół nie zna React Native
- Aplikacja nie wymaga złożonych UI

## Powiązane Tematy/Wzorce

- [React Native Patterns](./React_Native_Patterns.md)
- [Navigation Stacking](./Navigation_Stacking.md)
- [Performance Optimization](../performance/INDEX.md)
- [Mobile UI/UX](../mobile-ui/INDEX.md)
- [React Fundamentals](../react/INDEX.md)

## Źródła / Dalsza Lektura

- [React Native Layout System](https://reactnative.dev/docs/layout-props)
- [React Native Paper Portal](https://callstack.github.io/react-native-paper/portal.html)
- [Safe Area Context](https://github.com/th3rdwave/react-native-safe-area-context)
- [React Navigation Stacking](https://reactnavigation.org/docs/stack-navigator)
- [React Native Modal](https://reactnative.dev/docs/modal)
- [Android Elevation](https://developer.android.com/training/material/shadows-clipping)
- [iOS Shadow Properties](https://developer.apple.com/documentation/uikit/uiview/1622536-shadow)


