# diceware
Simple project for generating passwords using [Diceware](http://world.std.com/~reinhold/diceware.html).

## Building
To build, invoke following command:
```bash
mvn clean package
```

## Usage
To run, invoke following command:
```bash
java -jar diceware.jar min_password_length [word_list_file_name]
```

### Parameters
* `min_password_length` - minimum length of generated password
* `word_list_file_name` - (optional) file name containing word list; if not provided - default list will be used
