First version.

function toCamelCase(str) {
  // Zamiam string na tablicę znaków, aby umożliwić modyfikacje
  let chars = str.split('');
  
  for (let i = 0; i < chars.length; i++) {
    if (chars[i] === '_' || chars[i] === '-') {
      chars.splice(i, 1);
      if (i < chars.length) {
        chars[i] = chars[i].toUpperCase();
      }
    }
  }
  
  let result = chars.join('');
  return result;
} 

* Procesowanie krok po kroku: String jest najpierw zamieniany na tablicę, a następnie iterujemy przez każdy znak, aby zidentyfikować podkreślenia (_) lub myślniki (-).
* Modifikacja tablicy: Znalezione znaki są usuwane, a następne litery po usunięciu są zamieniane na wielkie litery.
* Dodatkowa pamięć: Funkcja korzysta z dodatkowej pamięci na tablicę chars, aby móc modyfikować ciąg znaków.
* Złożoność obliczeniowa: O(n) dla zamiany ciągu na tablicę, iteracji przez każdy znak oraz ponownego połączenia tablicy w ciąg.

Last version.

function toCamelCase(str) {
  return str.replace(/[-_](.)/g, (_, c) => c.toUpperCase());
}

* Automaty skończone: Regex działa jak automat skończony, co oznacza, że jest bardzo wydajny i działa bez konieczności tworzenia dodatkowej struktury danych, takiej jak tablica.
* Brak dodatkowej pamięci: Funkcja nie wymaga tworzenia dodatkowych struktur danych do przetwarzania, ponieważ zamiana zachodzi bezpośrednio na oryginalnym ciągu (technicznie, pod maską, przekształcenia odbywają się w pamięci, ale nie tworzysz jawnej tablicy).
* Złożoność obliczeniowa: Podobnie jak w pierwszej wersji, operacja ma złożoność O(n), ale jest bardziej efektywna ze względu na bezpośrednie operacje na ciągu przy pomocy automatu regex.
