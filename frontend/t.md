project-root/
├── public/
│ ├── index.html — Główny szablon HTML aplikacji, w którym montowany jest komponent root React oraz ładowane są metadane i style.
│ └── favicon.ico — Ikona wyświetlana na karcie przeglądarki.
├── src/
│ ├── api/ # Definicje zapytań HTTP
│ │ ├── products.js — Funkcje wysyłające requesty związane z produktami (wyszukiwanie, CRUD).
│ │ ├── categories.js — Zapytania do endpointów kategorii produktów.
│ │ ├── warehouses.js — Metody pobierające i modyfikujące dane magazynów.
│ │ ├── inventory.js — Operacje magazynowe (przyjęcia, transfer, dostawa).
│ │ ├── transactions.js — Wywołania API do listowania i szczegółów transakcji.
│ │ ├── clients.js — Requesty dotyczące klientów (lista, szczegóły, tworzenie).
│ │ ├── suppliers.js — Wywołania do zarządzania dostawcami i ich historią.
│ │ ├── employees.js — Zapytania dla CRUD pracowników i historii transakcji.
│ │ ├── dashboard.js — Pobieranie podsumowań i metryk dla kafelków dashboardu.
│ │ └── geography.js — Endpointy regionów, krajów i adresów.
│ ├── app/
│ │ ├── store.js — Konfiguracja Redux store z middleware i środkami.
│ │ └── rootReducer.js — Połączenie wszystkich slice’ów w jeden główny reducer.
│ ├── features/ # Slice’y Redux dla poszczególnych domen
│ │ ├── auth/
│ │ │ ├── authSlice.js — Reducer i akcje do autoryzacji admina oraz stanu zalogowania.
│ │ │ └── authAPI.js — Zapytania logowania, wylogowania i weryfikacji tokena.
│ │ ├── products/
│ │ │ └── productsSlice.js — Logika stanu produktów: pobieranie list, szczegóły, create/update/delete.
│ │ ├── categories/
│ │ │ └── categoriesSlice.js — Zarządzanie stanem kategorii: lista, edycja, usuwanie.
│ │ ├── warehouses/
│ │ │ └── warehousesSlice.js — Slice do stanu magazynów: dane, detale, CRUD.
│ │ ├── inventory/
│ │ │ ├── receiveSlice.js — Obsługa przyjęć od dostawców do magazynu.
│ │ │ ├── transferSlice.js — Logika stanów transferów między magazynami.
│ │ │ └── deliverySlice.js — Slice do dostaw do klientów i aktualizacji stanów.
│ │ ├── transactions/
│ │ │ └── transactionsSlice.js — Przechowuje listę transakcji i wyświetla szczegóły.
│ │ ├── clients/
│ │ │ └── clientsSlice.js — Stan klientów: lista, detale i tworzenie.
│ │ ├── suppliers/
│ │ │ └── suppliersSlice.js — Obsługa listy dostawców, historii i CRUD.
│ │ ├── employees/
│ │ │ └── employeesSlice.js — Slice ze stanem pracowników i ich historią.
│ │ └── dashboard/
│ │ └── summarySlice.js — Pobiera i przechowuje kafelki podsumowań dashboardu.
│ ├── components/ # Reużywalne komponenty UI
│ │ ├── Layout/
│ │ │ ├── Navbar.js — Pasek nawigacyjny ze ścieżkami i wylogowaniem.
│ │ │ ├── Sidebar.js — Menu boczne z linkami do poszczególnych stron.
│ │ │ └── ProtectedRoute.js — Komponent chroniący trasy dla zalogowanych.
│ │ ├── Tables/
│ │ │ ├── DataTable.js — Uniwersalna tabela z sortowaniem i filtrowaniem.
│ │ │ └── Pagination.js — Kontrolki stronicowania tabel.
│ │ ├── Forms/
│ │ │ ├── ProductForm.js — Formularz tworzenia/edycji produktu.
│ │ │ ├── CategoryForm.js — Formularz zarządzania kategoriami.
│ │ │ ├── WarehouseForm.js — Form do wprowadzania/edycji magazynu.
│ │ │ ├── InventoryForm.js — Wspólny formularz pozycji magazynowej.
│ │ │ ├── TransactionFilter.js — Komponent filtrowania transakcji.
│ │ │ ├── ClientForm.js — Formularz rejestracji/edycji klienta.
│ │ │ ├── SupplierForm.js — Formularz rejestracji/edycji dostawcy.
│ │ │ └── EmployeeForm.js — Inputy do tworzenia/edycji pracownika.
│ │ ├── Dashboard/
│ │ │ ├── SummaryTiles.js — Kafelki z najważniejszymi metrykami.
│ │ │ └── Charts.js — Wykresy trendów (np. obroty, zajętość).
│ │ └── Geography/
│ │ ├── RegionsList.js — Lista rozwijana regionów.
│ │ ├── CountriesList.js — Dropdown krajów zależnych od regionu.
│ │ └── AddressForm.js — Komponent do tworzenia adresu z polami.
│ ├── pages/ # Komponenty level-route
│ │ ├── LoginPage.js — Strona logowania z formularzem auth.
│ │ ├── DashboardPage.js — Główna strona panelu z kafelkami i wykresami.
│ │ ├── ProductsPage.js — Widok listy produktów z możliwością CRUD.
│ │ ├── ProductDetailPage.js — Szczegóły wybranego produktu z transakcjami.
│ │ ├── CategoriesPage.js — Zarządzanie kategoriami w tabeli.
│ │ ├── WarehousesPage.js — Lista i CRUD magazynów.
│ │ ├── WarehouseDetailPage.js — Widok detali magazynu z historią.
│ │ ├── InventoryReceivePage.js — Formularz przyjęcia towaru od dostawcy.
│ │ ├── InventoryTransferPage.js — UI do transferu między magazynami.
│ │ ├── InventoryDeliveryPage.js — Strona dostaw do klientów.
│ │ ├── TransactionsPage.js — Lista transakcji z opcją filtrowania.
│ │ ├── TransactionDetailPage.js — Szczegóły wybranej transakcji.
│ │ ├── ClientsPage.js — Lista klientów i szybki podgląd.
│ │ ├── ClientDetailPage.js — Historia zamówień i dane klienta.
│ │ ├── SuppliersPage.js — Zarządzanie dostawcami.
│ │ ├── SupplierDetailPage.js — Szczegóły dostawcy z historią.
│ │ ├── EmployeesPage.js — Widok pracowników z licznikiem transakcji.
│ │ ├── EmployeeDetailPage.js — Szczegóły profilu i historii transakcji.
│ │ └── GeographyPage.js — Interfejs wyboru regionów, krajów i adresów.
│ ├── routes/  
│ │ └── AppRouter.js — Definicje ścieżek React Router i ich ochrona.
│ ├── utils/  
│ │ ├── apiClient.js — Wrapper Axios/fetch z bazowym URL i interceptorami.
│ │ ├── authHeader.js — Funkcja do generowania nagłówków z tokenem JWT.
│ │ └── helpers.js — Pomocnicze funkcje (format dat, walidacje itp.).
│ ├── assets/  
│ │ ├── styles/  
│ │ │ ├── variables.css — Zmienne CSS (kolory, odstępy) do spójności stylów.
│ │ │ └── global.css — Globalne reguły CSS i reset stylów.
│ │ └── images/  
│ │ └── logo.png — Logo firmy wyświetlane w nawigacji.
│ ├── App.js — Główny komponent łączący router, providery i layout.
│ ├── index.js — Punkt wejścia, renderowanie App i podłączenie store.
│ └── setupTests.js — Konfiguracja Jest i RTL do testowania komponentów.
├── tests/  
│ ├── e2e/ — Testy end-to-end (np. Cypress) symulujące UX.
│ └── integration/ — Testy integracyjne dla slice’ów i komponentów.
├── .env.example — Wzorzec zmiennych środowiskowych (API URL, klucze).
├── .gitignore — Lista ignorowanych plików Git.
├── package.json — Zależności, skrypty i metadane projektu.
├── README.md — Krótki opis projektu, instrukcja setupu i uruchomienia.
└── yarn.lock — Dokładne wersje pakietów dla deterministycznego build'u.
