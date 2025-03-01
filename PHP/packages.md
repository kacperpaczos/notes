Namespaces in PHP help organize code and prevent conflicts between classes, functions, and constants. They work like unique identifiers for different parts of a project.  

Declaring a namespace in PHP is done using the `namespace` keyword at the beginning of a file. For example:  

```php
namespace MyApp\Utils;

class Logger {
    public function log($message) {
        echo $message;
    }
}
```  

To use a namespaced class in another file, the `use` statement simplifies access:  

```php
use MyApp\Utils\Logger;

$logger = new Logger();
$logger->log("Hello, world!");
```  

Without the `use` statement, the class must be referenced with its full namespace:  

```php
$logger = new \MyApp\Utils\Logger();
```  

Composer is PHP's dependency manager, allowing the installation and management of external libraries. A package can be installed using:  

```sh
composer require monolog/monolog
```  

This updates the `composer.json` file and installs the package inside the `vendor/` directory. The Composer autoloader makes it available automatically when required in the project:  

```php
require 'vendor/autoload.php';

use Monolog\Logger;
use Monolog\Handler\StreamHandler;

$log = new Logger('app');
$log->pushHandler(new StreamHandler('app.log', Logger::WARNING));
$log->warning('This is a warning!');
```  

PSR-4 is a standard that maps namespaces to directory structures. Instead of manually including files, Composer can autoload them based on their namespace. To enable PSR-4 autoloading, define it in `composer.json`:  

```json
{
    "autoload": {
        "psr-4": {
            "MyApp\\": "src/"
        }
    }
}
```  

Running the command  

```sh
composer dump-autoload
```  

generates the necessary autoloading mappings, allowing all classes in the `src/` directory under the `MyApp\` namespace to be loaded automatically without requiring manual inclusion.
