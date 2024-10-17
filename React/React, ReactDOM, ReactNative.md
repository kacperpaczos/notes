React: Jest biblioteką JavaScript do tworzenia interfejsów użytkownika (UI). Główne założenie Reacta to komponenty, które mogą mieć własny stan (state) i reagować na zmiany danych. Umożliwia to budowanie dynamicznych aplikacji webowych w sposób modularny. Kluczowy jest tu koncept VirtualDOM.

ReactDOM: To biblioteka, która obsługuje renderowanie komponentów Reacta w przeglądarce poprzez manipulowanie DOM. Jest kluczowa dla aplikacji webowych opartych na React.

React Native: Jest frameworkiem opartym na React, który umożliwia tworzenie natywnych aplikacji mobilnych (na iOS i Androida) z użyciem JavaScriptu. Używa podobnych komponentów, jak React, ale zamiast renderowania w DOM, generuje natywne widoki mobilne.

W kontekście wzorca MVC (Model-View-Controller), React pełni rolę warstwy View (Widok), odpowiedzialnej za wyświetlanie danych i interakcji z użytkownikiem. Jako że React jest odseparowany od warstwy modelu (M) i kontrolera (C), wymaga on osobnych mechanizmów do pozyskiwania i zarządzania danymi, np. przez fetchowanie danych z API.

Dane w React mogą być pobierane za pomocą:

Wbudowanych funkcji przeglądarki, takich jak fetch,
Bibliotek pełniących te same role, jak np. axios, które oferują dodatkowe funkcje (np. automatyczne parsowanie odpowiedzi JSON, obsługę nagłówków),
Bibliotek Reactowych jak SWR czy React Query, które wnoszą zaawansowane mechanizmy cache'owania, synchronizacji i aktualizacji danych w czasie rzeczywistym.

