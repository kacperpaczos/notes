# Użytkownik systemowy w Linux

Flaga `--system` podczas tworzenia użytkownika pozwala stworzyć specjalnego użytkownika systemowego, który:

- Nie może się logować do systemu
- Nie posiada hasła (hasło nie wygasa)
- Jest wykorzystywany głównie przez usługi działające na serwerze

Jest to standardowa praktyka przy konfiguracji usług systemowych, które wymagają dedykowanego użytkownika do działania.
