### FileDescriptorLeak

Разгледайте `FileDescriptorLeak.java` и се уверете, че примерът не затваря правилно ресурсите, с които борави. Пуснете примера. 

Уверете се, че файлови дескриптори остават незатворени, чрез:
```bash
$ lsof | grep helloworld
java      6450 foo    5w      REG                1,4          1 51119881 /private/tmp/helloworld.txt
java      6450 foo    6w      REG                1,4          1 51119881 /private/tmp/helloworld.txt
java      6450 foo    7w      REG                1,4          1 51119881 /private/tmp/helloworld.txt
```

Как бихме могли да оправим този пример?
