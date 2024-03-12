# RATP Android Technical Test

Android source code for RATP Technical Test.

## IDE
Android Studio Iguana | 2023.2.1

## App Architecture
MVVM with LiveData and DataBinding

## Libraries
### Dependency Injection: Dagger Hilt
### Networking: 
* Okhttp3: https://github.com/square/okhttp
* Retrofit2: https://github.com/square/retrofit
* NetworkResponseAdapter: https://github.com/haroldadmin/NetworkResponseAdapter
Used for wrapping API responses

## Good to know
Pagination in list mode is achieved by scrolling to the bottom of the list. Each time 10 rows are fetched from the API and added.
User can also do a pull to refresh from the top to empty and refill the list with only first 10 rows

## TBD
Offline access using a local database like Room. 
The repository class will need to be updated to handle saving data from the API to the database. And the UI we will whow the data from the database when available.
