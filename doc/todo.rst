Highlightr
==========

Server Side
-----------

- REST API implementation
    - Ability to increase rankModel rankModel using POST/PUT API
    - Ability to retrieve rankModel by topic and URL
    - Bulk querying

- Multiple handlers for different statistics ranks (@see http://sharedcount.com/documentation.php)
    - Stream specific ranks
    - Facebook
    - Delicious
    - StumbleUpon
    - LinkedIn
    - DZone
    - YCombinator

- Caching
    - Cache invalidation times should be different for different caches (tracking vs transforming vs statistics)
    - Recent items caching vs old items caching

- Logging configuration and level checks

- Administration
    - https://github.com/codahale/metrics [?]
    - Check possible JMX solutions [!]
    - Simple admin UI
    - Investigate caching memory leaks [!]


Client Side
-----------

- Chrome extension creation
    - Packaging and distribution [!]
    - Bulk updates
    - Extract styles into separate CSS if it's possible