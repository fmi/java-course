## Hashtag Extractor #️⃣

### Условие

Социалните мрежи са пълни с hashtag-ове. Дали ще е пост на някой приятел в Instagram с хаштагове за последната му почивка,
или поредния tiktok, който има задължителния #fyp, хаштаговете са мощен инструмент за
организиране на съдържание и достигане до нова аудитория - и някъде на backend-а
някой трябва да ги извади, да ги подреди и да ги преброи.

Вие ще трябва да напишете точно тази част. По подаден текст на една публикация извлечете всеки
hashtag, който се съдържа в нея.

За да сме прецизни какво точно е hashtag:

- той **започва** от символ `#`;
- непосредствено след него следват **една или повече** букви или цифри - *тялото*
  на тага (например в `#java2024` тялото е `java2024`); тяло, съставено **изцяло от цифри**, също е валидно (така че
  `#2024` е таг с тяло `2024`);
- той **завършва** веднага щом се появи символ, който не е буква или цифра:
  интервал, пунктуация като `!` или `.`, краят на текста или дори нов `#` (така
  че в `#one#two` първият таг завършва точно там, където започва вторият `#`);
- самият `#` е само маркер - той **не** е част от тага, който връщате.

Таговете, които връщате, трябва да са:

- **с малки букви** (така че `#Java` и `#java` са един и същ таг),
- **без** водещия `#`,
- **без повторения**, като се запазва редът, в който всеки таг е **видян за пръв
  път**.

Самостоятелен `#`, който не е последван от буква или цифра, **не** е hashtag и
просто се игнорира. Ако постът е `null`, празен или просто не съдържа hashtag-ове, върнете празен
масив (с дължина `0`, а не `null`).

Създайте публичен клас `HashtagExtractor` с метода

```java
/**
 * Extracts all hashtags from a social-media post.
 *
 * A hashtag begins at a '#', has a body of one or more letters/digits, and ends
 * at the first character that is not a letter or digit. The returned tags are
 * lowercased, stripped of the leading '#', and de-duplicated in first-seen
 * order. A lone '#' is ignored.
 *
 * @param post the text of the post;
 * @return the extracted tags (lowercased, without '#', de-duplicated, in
 *         first-seen order), or an empty array if the post contains no hashtags
 */
public static String[] extractHashtags(String post) {
}
```

### Примери

| Извикване                                                 | Резултат          |
|:----------------------------------------------------------|:------------------|
| `extractHashtags("Loving #Java and #java at #FMI!")`      | `{"java", "fmi"}` |
| `extractHashtags("No tags here, just vibes")`             | `{}`              |
| `extractHashtags("#MJT #mjt #MjT are all one tag")`       | `{"mjt"}`         |
| `extractHashtags("Adjacent #one#two tags")`               | `{"one", "two"}`  |
| `extractHashtags("A lone # is not a tag, but #this1 is")` | `{"this1"}`       |
| `extractHashtags("Trailing hash #")`                      | `{}`              |
| `extractHashtags("   ")`                                  | `{}`              |
| `extractHashtags("Happy new year #2024!")`                | `{"2024"}`        |

### ⚠️ Забележки

- Използването на регулярни изрази (регекси) — класовете `Pattern` и `Matcher`,
  както и методите на `String`, които работят с тях (`matches`, `split`,
  `replaceAll`, `replaceFirst`), **не е позволено**.
- Използването на структури от данни, различни от масиви (list-ове, set-ове,
  map-ове, ...), както и на comparator-и, lambda изрази и подобни, **не е
  позволено**. Тази задача може и трябва да бъде решена със знанията от първата
  лекция на курса.
