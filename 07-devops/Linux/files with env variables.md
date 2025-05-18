Oto skondensowana lista plików systemowych Linuxa wraz z krótkimi opisami i przykładowymi danymi:

/etc/os-release - Informacje o systemie operacyjnym. Przykład: NAME="Ubuntu", VERSION="20.04 LTS".

/etc/hostname - Nazwa hosta komputera. Przykład: my-computer.

/etc/hosts - Mapowanie nazw hostów na adresy IP. Przykład: 127.0.0.1 localhost.

/etc/resolv.conf - Konfiguracja DNS. Przykład: nameserver 8.8.8.8.

/etc/shells - Lista dostępnych powłok. Przykład: /bin/bash, /bin/sh.

/etc/passwd - Informacje o użytkownikach systemu. Przykład: user:x:1000:1000::/home/user:/bin/bash.

/etc/shadow - Zaszyfrowane hasła użytkowników. Przykład: root:$6$E9t...8/:18842:0:99999:7::.

/etc/group - Informacje o grupach użytkowników. Przykład: sudo:x:27:user.

/etc/login.defs - Konfiguracja logowania i kont użytkowników. Przykład: PASS_MAX_DAYS 99999.

/etc/issue - Wiadomość przed ekranem logowania. Przykład: Ubuntu 20.04.3 LTS.

/etc/network/interfaces - Konfiguracja interfejsów sieciowych (Debian/Ubuntu). Przykład: auto eth0, iface eth0 inet dhcp.

/etc/sysconfig/network-scripts/ifcfg-eth0 - Konfiguracja sieci (Red Hat/CentOS). Przykład: DEVICE=eth0, BOOTPROTO=dhcp.

/etc/fstab - Lista systemów plików montowanych przy starcie. Przykład: UUID=abc-123 / ext4 defaults 0 1.

/etc/crontab - Konfiguracja zadań cron. Przykład: 0 5 * * * root /usr/bin/backup.sh.

/etc/sudoers - Ustawienia uprawnień sudo. Przykład: user ALL=(ALL) ALL.
