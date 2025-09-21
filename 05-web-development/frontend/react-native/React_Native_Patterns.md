# Wzorce React Native - Najlepsze Praktyki

## Cel
Kompleksowy przewodnik po wzorcach i najlepszych praktykach w React Native - od podstawowych komponentów po zaawansowane architektury.

## Problem
Potrzeba systematycznego podejścia do rozwoju aplikacji React Native:
- Struktura projektu i organizacja kodu
- Wzorce komponentów i zarządzanie stanem
- Optymalizacja wydajności
- Nawigacja i routing
- Integracja z natywnymi funkcjami

## Pojęcia Kluczowe

### Architektura
- **Component-based** - architektura oparta na komponentach
- **Unidirectional data flow** - jednokierunkowy przepływ danych
- **Container/Presenter** - separacja logiki i prezentacji
- **HOC (Higher-Order Components)** - komponenty wyższego rzędu
- **Render Props** - wzorzec render props

### Zarządzanie Stanem
- **Local State** - stan lokalny komponentu
- **Context API** - globalny stan aplikacji
- **Redux** - przewidywalny kontener stanu
- **MobX** - reaktywne zarządzanie stanem
- **Zustand** - lekka biblioteka stanu

### Nawigacja
- **React Navigation** - standardowa biblioteka nawigacji
- **Stack Navigator** - nawigacja stosowa
- **Tab Navigator** - nawigacja zakładek
- **Drawer Navigator** - nawigacja boczna
- **Deep Linking** - linkowanie głębokie

## Struktura / Diagram

```
React Native App
├── src/
│   ├── components/          # Komponenty wielokrotnego użytku
│   │   ├── common/         # Podstawowe komponenty
│   │   ├── forms/          # Komponenty formularzy
│   │   └── layout/         # Komponenty układu
│   ├── screens/            # Ekrany aplikacji
│   ├── navigation/         # Konfiguracja nawigacji
│   ├── services/           # Usługi (API, storage)
│   ├── store/              # Zarządzanie stanem
│   ├── hooks/              # Custom hooks
│   ├── utils/              # Funkcje pomocnicze
│   ├── constants/          # Stałe aplikacji
│   └── types/              # Definicje typów
├── assets/                 # Zasoby (obrazy, czcionki)
└── __tests__/              # Testy
```

## Przepływ Działania

### 1. Struktura Komponentu
```jsx
// components/common/Button.jsx
import React from 'react';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';

const Button = ({ 
  title, 
  onPress, 
  variant = 'primary', 
  size = 'medium',
  disabled = false,
  style,
  textStyle,
  ...props 
}) => {
  return (
    <TouchableOpacity
      style={[
        styles.button,
        styles[variant],
        styles[size],
        disabled && styles.disabled,
        style,
      ]}
      onPress={onPress}
      disabled={disabled}
      {...props}
    >
      <Text style={[styles.text, styles[`${variant}Text`], textStyle]}>
        {title}
      </Text>
    </TouchableOpacity>
  );
};

Button.propTypes = {
  title: PropTypes.string.isRequired,
  onPress: PropTypes.func.isRequired,
  variant: PropTypes.oneOf(['primary', 'secondary', 'outline']),
  size: PropTypes.oneOf(['small', 'medium', 'large']),
  disabled: PropTypes.bool,
  style: PropTypes.object,
  textStyle: PropTypes.object,
};

const styles = StyleSheet.create({
  button: {
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  primary: {
    backgroundColor: '#007AFF',
  },
  secondary: {
    backgroundColor: '#6C757D',
  },
  outline: {
    backgroundColor: 'transparent',
    borderWidth: 1,
    borderColor: '#007AFF',
  },
  small: {
    paddingHorizontal: 12,
    paddingVertical: 8,
  },
  medium: {
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  large: {
    paddingHorizontal: 24,
    paddingVertical: 16,
  },
  disabled: {
    opacity: 0.5,
  },
  text: {
    fontWeight: '600',
  },
  primaryText: {
    color: 'white',
  },
  secondaryText: {
    color: 'white',
  },
  outlineText: {
    color: '#007AFF',
  },
});

export default Button;
```

### 2. Container/Presenter Pattern
```jsx
// screens/UserList/UserListContainer.jsx
import React, { useState, useEffect } from 'react';
import { View, FlatList, Alert } from 'react-native';
import UserListPresenter from './UserListPresenter';
import { userService } from '../../services/userService';

const UserListContainer = ({ navigation }) => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      setLoading(true);
      const data = await userService.getUsers();
      setUsers(data);
    } catch (error) {
      Alert.alert('Błąd', 'Nie udało się załadować użytkowników');
    } finally {
      setLoading(false);
    }
  };

  const handleRefresh = async () => {
    setRefreshing(true);
    await loadUsers();
    setRefreshing(false);
  };

  const handleUserPress = (user) => {
    navigation.navigate('UserDetail', { userId: user.id });
  };

  const handleDeleteUser = async (userId) => {
    Alert.alert(
      'Usuń użytkownika',
      'Czy na pewno chcesz usunąć tego użytkownika?',
      [
        { text: 'Anuluj', style: 'cancel' },
        {
          text: 'Usuń',
          style: 'destructive',
          onPress: async () => {
            try {
              await userService.deleteUser(userId);
              setUsers(users.filter(user => user.id !== userId));
            } catch (error) {
              Alert.alert('Błąd', 'Nie udało się usunąć użytkownika');
            }
          },
        },
      ]
    );
  };

  return (
    <UserListPresenter
      users={users}
      loading={loading}
      refreshing={refreshing}
      onRefresh={handleRefresh}
      onUserPress={handleUserPress}
      onDeleteUser={handleDeleteUser}
    />
  );
};

export default UserListContainer;

// screens/UserList/UserListPresenter.jsx
import React from 'react';
import { View, FlatList, Text, StyleSheet } from 'react-native';
import { Card, Title, Paragraph, Button } from 'react-native-paper';
import LoadingSpinner from '../../components/common/LoadingSpinner';

const UserListPresenter = ({
  users,
  loading,
  refreshing,
  onRefresh,
  onUserPress,
  onDeleteUser,
}) => {
  const renderUser = ({ item }) => (
    <Card style={styles.card} onPress={() => onUserPress(item)}>
      <Card.Content>
        <Title>{item.name}</Title>
        <Paragraph>{item.email}</Paragraph>
      </Card.Content>
      <Card.Actions>
        <Button onPress={() => onUserPress(item)}>Zobacz</Button>
        <Button onPress={() => onDeleteUser(item.id)}>Usuń</Button>
      </Card.Actions>
    </Card>
  );

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={users}
        renderItem={renderUser}
        keyExtractor={(item) => item.id.toString()}
        refreshing={refreshing}
        onRefresh={onRefresh}
        contentContainerStyle={styles.list}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  list: {
    padding: 16,
  },
  card: {
    marginBottom: 16,
  },
});

export default UserListPresenter;
```

### 3. Custom Hooks
```jsx
// hooks/useApi.js
import { useState, useEffect, useCallback } from 'react';

const useApi = (apiFunction, dependencies = []) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const result = await apiFunction();
      setData(result);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  }, dependencies);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const refetch = useCallback(() => {
    fetchData();
  }, [fetchData]);

  return { data, loading, error, refetch };
};

// hooks/useAsyncStorage.js
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useState, useEffect } from 'react';

const useAsyncStorage = (key, initialValue) => {
  const [storedValue, setStoredValue] = useState(initialValue);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadValue();
  }, [key]);

  const loadValue = async () => {
    try {
      const item = await AsyncStorage.getItem(key);
      if (item !== null) {
        setStoredValue(JSON.parse(item));
      }
    } catch (error) {
      console.error('Error loading from AsyncStorage:', error);
    } finally {
      setLoading(false);
    }
  };

  const setValue = async (value) => {
    try {
      setStoredValue(value);
      await AsyncStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error('Error saving to AsyncStorage:', error);
    }
  };

  const removeValue = async () => {
    try {
      setStoredValue(initialValue);
      await AsyncStorage.removeItem(key);
    } catch (error) {
      console.error('Error removing from AsyncStorage:', error);
    }
  };

  return [storedValue, setValue, removeValue, loading];
};

// hooks/usePermissions.js
import { useState, useEffect } from 'react';
import { Platform, PermissionsAndroid } from 'react-native';
import { request, PERMISSIONS, RESULTS } from 'react-native-permissions';

const usePermissions = (permissionType) => {
  const [permissionStatus, setPermissionStatus] = useState('denied');

  useEffect(() => {
    checkPermission();
  }, [permissionType]);

  const checkPermission = async () => {
    try {
      let result;
      
      if (Platform.OS === 'android') {
        result = await PermissionsAndroid.check(permissionType);
        setPermissionStatus(result ? 'granted' : 'denied');
      } else {
        result = await request(permissionType);
        setPermissionStatus(result);
      }
    } catch (error) {
      console.error('Permission check error:', error);
      setPermissionStatus('denied');
    }
  };

  const requestPermission = async () => {
    try {
      let result;
      
      if (Platform.OS === 'android') {
        result = await PermissionsAndroid.request(permissionType);
        setPermissionStatus(result === 'granted' ? 'granted' : 'denied');
      } else {
        result = await request(permissionType);
        setPermissionStatus(result);
      }
      
      return result === 'granted' || result === RESULTS.GRANTED;
    } catch (error) {
      console.error('Permission request error:', error);
      return false;
    }
  };

  return {
    permissionStatus,
    isGranted: permissionStatus === 'granted' || permissionStatus === RESULTS.GRANTED,
    requestPermission,
    checkPermission,
  };
};
```

## Przykłady Użycia

### Navigation Patterns
```jsx
// navigation/AppNavigator.jsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createDrawerNavigator } from '@react-navigation/drawer';
import Icon from 'react-native-vector-icons/MaterialIcons';

// Screens
import HomeScreen from '../screens/Home/HomeScreen';
import ProfileScreen from '../screens/Profile/ProfileScreen';
import SettingsScreen from '../screens/Settings/SettingsScreen';
import UserDetailScreen from '../screens/UserDetail/UserDetailScreen';

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();
const Drawer = createDrawerNavigator();

// Tab Navigator
const TabNavigator = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      tabBarIcon: ({ focused, color, size }) => {
        let iconName;

        if (route.name === 'Home') {
          iconName = 'home';
        } else if (route.name === 'Profile') {
          iconName = 'person';
        } else if (route.name === 'Settings') {
          iconName = 'settings';
        }

        return <Icon name={iconName} size={size} color={color} />;
      },
      tabBarActiveTintColor: '#007AFF',
      tabBarInactiveTintColor: 'gray',
    })}
  >
    <Tab.Screen 
      name="Home" 
      component={HomeScreen}
      options={{ title: 'Główna' }}
    />
    <Tab.Screen 
      name="Profile" 
      component={ProfileScreen}
      options={{ title: 'Profil' }}
    />
    <Tab.Screen 
      name="Settings" 
      component={SettingsScreen}
      options={{ title: 'Ustawienia' }}
    />
  </Tab.Navigator>
);

// Stack Navigator
const StackNavigator = () => (
  <Stack.Navigator
    screenOptions={{
      headerStyle: {
        backgroundColor: '#007AFF',
      },
      headerTintColor: '#fff',
      headerTitleStyle: {
        fontWeight: 'bold',
      },
    }}
  >
    <Stack.Screen 
      name="MainTabs" 
      component={TabNavigator}
      options={{ headerShown: false }}
    />
    <Stack.Screen 
      name="UserDetail" 
      component={UserDetailScreen}
      options={{ title: 'Szczegóły użytkownika' }}
    />
  </Stack.Navigator>
);

// Drawer Navigator
const DrawerNavigator = () => (
  <Drawer.Navigator
    screenOptions={{
      headerStyle: {
        backgroundColor: '#007AFF',
      },
      headerTintColor: '#fff',
    }}
  >
    <Drawer.Screen 
      name="MainStack" 
      component={StackNavigator}
      options={{ title: 'Aplikacja' }}
    />
  </Drawer.Navigator>
);

// Main App Navigator
const AppNavigator = () => (
  <NavigationContainer>
    <DrawerNavigator />
  </NavigationContainer>
);

export default AppNavigator;
```

### State Management with Context
```jsx
// store/AuthContext.jsx
import React, { createContext, useContext, useReducer, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { authService } from '../services/authService';

const AuthContext = createContext();

const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN_START':
      return { ...state, loading: true, error: null };
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        loading: false,
        isAuthenticated: true,
        user: action.payload.user,
        token: action.payload.token,
        error: null,
      };
    case 'LOGIN_FAILURE':
      return {
        ...state,
        loading: false,
        isAuthenticated: false,
        user: null,
        token: null,
        error: action.payload,
      };
    case 'LOGOUT':
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        token: null,
        error: null,
      };
    case 'RESTORE_TOKEN':
      return {
        ...state,
        token: action.payload,
        isAuthenticated: !!action.payload,
      };
    default:
      return state;
  }
};

const initialState = {
  isAuthenticated: false,
  user: null,
  token: null,
  loading: false,
  error: null,
};

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, initialState);

  useEffect(() => {
    restoreToken();
  }, []);

  const restoreToken = async () => {
    try {
      const token = await AsyncStorage.getItem('authToken');
      if (token) {
        dispatch({ type: 'RESTORE_TOKEN', payload: token });
      }
    } catch (error) {
      console.error('Token restore error:', error);
    }
  };

  const login = async (email, password) => {
    try {
      dispatch({ type: 'LOGIN_START' });
      const response = await authService.login(email, password);
      
      await AsyncStorage.setItem('authToken', response.token);
      
      dispatch({
        type: 'LOGIN_SUCCESS',
        payload: {
          user: response.user,
          token: response.token,
        },
      });
    } catch (error) {
      dispatch({
        type: 'LOGIN_FAILURE',
        payload: error.message,
      });
    }
  };

  const logout = async () => {
    try {
      await AsyncStorage.removeItem('authToken');
      dispatch({ type: 'LOGOUT' });
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  const value = {
    ...state,
    login,
    logout,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
```

## Implementacja (Fragmenty Kodu)

### Performance Optimization
```jsx
// components/optimized/ImageList.jsx
import React, { memo, useCallback, useMemo } from 'react';
import { FlatList, Image, View, Text, StyleSheet } from 'react-native';

const ImageListItem = memo(({ item, onPress }) => {
  const handlePress = useCallback(() => {
    onPress(item);
  }, [item, onPress]);

  return (
    <View style={styles.item}>
      <Image
        source={{ uri: item.thumbnail }}
        style={styles.thumbnail}
        resizeMode="cover"
      />
      <Text style={styles.title} numberOfLines={2}>
        {item.title}
      </Text>
    </View>
  );
});

const ImageList = ({ data, onItemPress }) => {
  const keyExtractor = useCallback((item) => item.id.toString(), []);
  
  const renderItem = useCallback(({ item }) => (
    <ImageListItem item={item} onPress={onItemPress} />
  ), [onItemPress]);

  const memoizedData = useMemo(() => data, [data]);

  return (
    <FlatList
      data={memoizedData}
      renderItem={renderItem}
      keyExtractor={keyExtractor}
      numColumns={2}
      showsVerticalScrollIndicator={false}
      contentContainerStyle={styles.list}
      getItemLayout={(data, index) => ({
        length: 200,
        offset: 200 * index,
        index,
      })}
      removeClippedSubviews={true}
      maxToRenderPerBatch={10}
      windowSize={10}
    />
  );
};

const styles = StyleSheet.create({
  list: {
    padding: 16,
  },
  item: {
    flex: 1,
    margin: 8,
    backgroundColor: 'white',
    borderRadius: 8,
    overflow: 'hidden',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.22,
    shadowRadius: 2.22,
  },
  thumbnail: {
    width: '100%',
    height: 150,
  },
  title: {
    padding: 12,
    fontSize: 14,
    fontWeight: '600',
  },
});

export default memo(ImageList);
```

### Error Boundary
```jsx
// components/ErrorBoundary.jsx
import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    console.error('ErrorBoundary caught an error:', error, errorInfo);
    // Tutaj można dodać logowanie do serwisu (np. Sentry, Crashlytics)
  }

  handleRetry = () => {
    this.setState({ hasError: false, error: null });
  };

  render() {
    if (this.state.hasError) {
      return (
        <View style={styles.container}>
          <Text style={styles.title}>Ups! Coś poszło nie tak</Text>
          <Text style={styles.message}>
            Wystąpił nieoczekiwany błąd. Spróbuj ponownie.
          </Text>
          <TouchableOpacity style={styles.button} onPress={this.handleRetry}>
            <Text style={styles.buttonText}>Spróbuj ponownie</Text>
          </TouchableOpacity>
        </View>
      );
    }

    return this.props.children;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 16,
    textAlign: 'center',
  },
  message: {
    fontSize: 16,
    textAlign: 'center',
    marginBottom: 24,
    color: '#666',
  },
  button: {
    backgroundColor: '#007AFF',
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 8,
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: '600',
  },
});

export default ErrorBoundary;
```

### Form Handling
```jsx
// components/forms/FormField.jsx
import React, { useState, useCallback } from 'react';
import { View, TextInput, Text, StyleSheet } from 'react-native';

const FormField = ({
  label,
  value,
  onChangeText,
  placeholder,
  secureTextEntry = false,
  keyboardType = 'default',
  error,
  ...props
}) => {
  const [focused, setFocused] = useState(false);

  const handleFocus = useCallback(() => {
    setFocused(true);
  }, []);

  const handleBlur = useCallback(() => {
    setFocused(false);
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.label}>{label}</Text>
      <TextInput
        style={[
          styles.input,
          focused && styles.inputFocused,
          error && styles.inputError,
        ]}
        value={value}
        onChangeText={onChangeText}
        placeholder={placeholder}
        secureTextEntry={secureTextEntry}
        keyboardType={keyboardType}
        onFocus={handleFocus}
        onBlur={handleBlur}
        {...props}
      />
      {error && <Text style={styles.errorText}>{error}</Text>}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: 16,
  },
  label: {
    fontSize: 16,
    fontWeight: '600',
    marginBottom: 8,
    color: '#333',
  },
  input: {
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 12,
    fontSize: 16,
    backgroundColor: 'white',
  },
  inputFocused: {
    borderColor: '#007AFF',
  },
  inputError: {
    borderColor: '#FF3B30',
  },
  errorText: {
    color: '#FF3B30',
    fontSize: 14,
    marginTop: 4,
  },
});

export default FormField;
```

## Zalety

### Struktura i Organizacja
- **Modularność** - łatwe do utrzymania komponenty
- **Reużywalność** - komponenty wielokrotnego użytku
- **Skalowalność** - łatwe dodawanie nowych funkcji
- **Testowalność** - jasne separacje odpowiedzialności

### Wydajność
- **Optymalizacja** - memo, useCallback, useMemo
- **Lazy loading** - opóźnione ładowanie komponentów
- **Virtualization** - wirtualizacja długich list
- **Image optimization** - optymalizacja obrazów

### Developer Experience
- **TypeScript** - statyczne typowanie
- **Hot reload** - szybki rozwój
- **Debugging** - narzędzia deweloperskie
- **Testing** - łatwe testowanie

## Wady

### Złożoność
- **Krzywa uczenia** - wymaga znajomości React i React Native
- **Setup** - skomplikowana konfiguracja
- **Debugging** - trudniejsze debugowanie natywnych problemów
- **Performance** - potencjalne problemy z wydajnością

### Ograniczenia
- **Natywne funkcje** - ograniczony dostęp do API
- **Platform differences** - różnice między iOS i Android
- **Bundle size** - większy rozmiar aplikacji
- **Dependencies** - zależność od zewnętrznych bibliotek

## Kiedy Używać / Kiedy Nie

### Używać gdy:
- Potrzebujesz aplikacji cross-platform
- Zespół zna React
- Szybki czas rozwoju
- Proste do średnio złożone aplikacje
- Chcesz współdzielić kod między platformami

### Nie używać gdy:
- Bardzo złożone aplikacje natywne
- Krytyczne wymagania wydajnościowe
- Potrzebujesz pełnego dostępu do API
- Zespół ma doświadczenie tylko z natywnym rozwojem
- Ograniczone zasoby na naukę

## Powiązane Tematy/Wzorce

- [Stacking Theory](./Stacking_Theory.md)
- [React Fundamentals](../react/INDEX.md)
- [Mobile Development Patterns](../mobile-patterns/INDEX.md)
- [Performance Optimization](../performance/INDEX.md)
- [State Management Patterns](../state-management/INDEX.md)

## Źródła / Dalsza Lektura

- [React Native Documentation](https://reactnative.dev/docs/getting-started)
- [React Navigation](https://reactnavigation.org/)
- [React Native Paper](https://callstack.github.io/react-native-paper/)
- [Expo Documentation](https://docs.expo.dev/)
- [React Native Performance](https://reactnative.dev/docs/performance)
- [React Native Best Practices](https://github.com/react-native-community/discussions-and-proposals)


