# mixed-content

## Cel

Wyjaśnienie polityki mixed content w przeglądarkach i jej konsekwencji.

## Krótka reguła

- HTTP strona → HTTPS żądanie: dozwolone (brak obniżenia poziomu bezpieczeństwa).
- HTTPS strona → HTTP żądanie: blokowane jako mixed content.

## Dlaczego

Żądania HTTP z kontekstu HTTPS obniżają bezpieczeństwo (ryzyko MITM, utrata poufności/integralności), więc przeglądarki je blokują.

## Przykłady

- HTML: `<img src="http://example.com/a.png">` na stronie HTTPS → blokada.
- JS: `fetch('http://api.example.com')` na stronie HTTPS → blokada (Mixed Content / TypeError).

## Jak naprawić

- Serwuj wszystkie zasoby wyłącznie po HTTPS.
- Włącz HSTS (Strict-Transport-Security).
- Użyj CSP: `upgrade-insecure-requests`, `block-all-mixed-content`.
- Wymuś 301/308 do HTTPS na serwerze i w CDN.

## Źródła / dalsza lektura

- MDN: "Mixed content"
- W3C: "Mixed Content"


