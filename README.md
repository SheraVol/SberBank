# SberBank
Были созданы регистрация клиентов и регистрация их карт, возможность аннулирования карты. Был использован HashMap, подключена бд MySQL для хранения данных клиентов и карт, подключен JdbcTemplate. При завершении срока годности карты данные клиента( ФИО, почта) записываются в файл с сообщением о блокировке карты. При удалении карты генерируется новая с сроком годности пять лет с дня ее генерации и новым номером карты. 