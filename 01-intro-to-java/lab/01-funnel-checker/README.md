## Фуния :arrow_forward:

### Условие

Създайте публичен клас `FunnelChecker` с метод

```java
public static boolean isFunnel(String str1, String str2)
```

който по дадени два низ от символи `str1` и `str2` определя дали вторият низ може да бъде получен от първия чрез премахването на един-единствен символ. 

### Примери

| Извикване                       | Резултат   |
|:------------------------------- |:---------- |
| `isFunnel("leave", "eave")`     | `true`     |
| `isFunnel("reset", "rest")`     | `true`     |
| `isFunnel("dragoon", "dragon")` | `true`     |
| `isFunnel("eave", "leave")`     | `false`    |
| `isFunnel("sleet", "lets")`     | `false`    |
| `isFunnel("skiff", "ski")`      | `false`    |
