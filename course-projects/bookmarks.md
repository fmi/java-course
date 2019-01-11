# Bookmarks Manager :paperclip:

## Условие

Създайте приложение за удобно съхранение и организиране на линкове (*bookmarks*).

### Сървър

Предоставя следните функционалности на клиента:
- регистриране с потребителско име и парола
- login
- съхраняване (*storage*) на линкове 
- добавяне на нови линкове
- премахване на линкове
- групиране на линкове в *колекции*
- търсене на линкове по заглавие или ключова дума

Линковете на потребителите трябва да се съхраняват във файлове на сървъра.

Всеки добавен линк трябва да има *заглавие*. То трябва да отговаря на съдържанието на **<title\>** таг-а от `html`-а на страницата.

При добавяне на нов линк, сървърът трябва да `extract`-не ключовите думи от страницата, за да могат потребителите да търсят по тях.

Ключовите думи са най-използваните думи в страницата. Погрижете се предварително да премахнете пунктуационни знаци, специални символи, `stopwords` и т.н.

Опитайте се да сведете думите до техните корени, използвайки някой [`stemming`](https://en.wikipedia.org/wiki/Stemming) алгоритъм. Най-простият от тях, който можете да използвате е `Suffix-stripping algorithm`:
> - if the word ends in 'ed', remove the 'ed'
> - if the word ends in 'ing', remove the 'ing'
> - if the word ends in 'ly', remove the 'ly'

### Клиент

Клиентът трябва да има `command line interface` със следните команди:

```bash
register <username> <password>
login <username> <password>
make-collection <collection-name>
add-to <collection> <link>
remove-from <collection> <link>
search -tags <tag1> <tag2> <tag3> ...
search -title <title> - връща всички линкове, в чиито заглавия се среща <title>
import-from-chrome - добавя всички bookmarks от Google Chrome
```

`Google Chrome` съхранява вашите `bookmarks` на файловата система в `json` формат. Файлът с `bookmarks` се намира в различни директории в зависимост от операционната система:
- **Windows** - `AppData\Local\Google\Chrome\User Data\Default`
- **Linux** - `~/.config/google-chrome/Default/`
- **MacOS** - `/Users/<Your UserName>/Library/Application\ Support/Google/Chrome`

## Submission

Качете `.zip` архив на познатите папки `src`, `test` и `resources` (опционално, ако имате допълнителни файлове, които не са .java) в sapera.org.
Там няма да има автоматизирани тестове.
Проектът ви трябва да е качен в грейдъра не по-късно от 18:00 в деня преди датата на защитата.

Успех!
