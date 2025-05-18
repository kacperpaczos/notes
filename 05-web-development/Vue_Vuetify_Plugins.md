# Vue, Vuetify, Pluginy i OData

## Vue.js
- **Definicja**: Progresywny framework JavaScript do budowania interfejsów użytkownika
- **Główne cechy**:
  - Reaktywność - automatyczne aktualizacje UI gdy zmienia się stan
  - Komponenty - enkapsulacja logiki, szablonu i stylu
  - Jednokierunkowy przepływ danych
  - Virtual DOM dla wydajności
- **Lifecycle Hooks**: `created`, `mounted`, `updated`, `destroyed` itd.

## Vuetify
- **Definicja**: Biblioteka komponentów Material Design dla Vue.js
- **Cechy**:
  - Gotowe komponenty UI zgodne z Material Design
  - Responsywny grid system
  - Bogate API dla każdego komponentu
  - Wsparcie dla dużych aplikacji
- **Przykłady komponentów**: v-btn, v-card, v-data-table, v-navigation-drawer
- **Użycie**:
  ```javascript
  // main.js
  import Vue from 'vue'
  import Vuetify from 'vuetify'
  import 'vuetify/dist/vuetify.min.css'
  
  Vue.use(Vuetify)
  
  // W komponencie
  <v-btn color="primary">Przycisk</v-btn>
  ```

## Pluginy w Vue.js
- **Definicja**: Kod rozszerzający funkcjonalność Vue lub dodający globalne funkcje
- **Typy pluginów**:
  1. **Pluginy dodające globalne metody/właściwości**
  2. **Pluginy dodające globalne zasoby** (dyrektywy, filtry, komponenty)
  3. **Pluginy dodające opcje komponentów** (mixin)
  4. **Pluginy dostarczające API** (Vue Router, Vuex)
- **Tworzenie pluginu**:
  ```javascript
  const MyPlugin = {
    install(Vue, options) {
      // Dodanie globalnej metody
      Vue.myGlobalMethod = function() {}
      
      // Dodanie właściwości do prototypu Vue
      Vue.prototype.$myProperty = 'value'
      
      // Dodanie dyrektywy
      Vue.directive('my-directive', {})
      
      // Dodanie komponentu
      Vue.component('my-component', {})
    }
  }
  
  // Użycie
  Vue.use(MyPlugin, { opcje })
  ```

## OData (Open Data Protocol)
- **Definicja**: Protokół otwarty do tworzenia i korzystania z usług RESTful
- **Cechy**:
  - Standardowy sposób zapytań przez URL
  - Filtrowanie, sortowanie, paginacja, projekcja
  - Wsparcie dla operacji CRUD
  - Format JSON lub XML
- **Przykłady zapytań**:
  - `GET /Products` - wszystkie produkty
  - `GET /Products?$filter=Price gt 10` - filtrowanie
  - `GET /Products?$orderby=Name` - sortowanie
  - `GET /Products?$top=10&$skip=10` - paginacja
  - `GET /Products?$select=Name,Price` - projekcja
- **Integracja z Vue**:
  - Użycie bibliotek Axios, Fetch API
  - Biblioteki dedykowane OData (odata-client, o.js) 