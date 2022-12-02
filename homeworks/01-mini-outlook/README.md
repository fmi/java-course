# Домашно 1

## MiniOutlook :mailbox_closed:

`Краен срок: 11.12.2022, 23:59`

### Условие

Ще създадем mail client приложение, което може да работи с множество мейл акаунти и поддържа следните функционалности:
- Съществуват папки по подразбиране - `inbox` и `sent` - в първата се намират всички получени мейли, а във втората - всички изпратени.

- Могат да се създават нови папки в папката inbox с неограничено ниво на влагане, т.е. може да създадем папка "important", а вътре в нея да има две други папки - "work" и "personal":

    ```bash
    inbox
    └── important
        ├── personal
        └── work
    ```
    
- Може да се създаде нова папка, само ако всички папки в йерархията над нея вече съществуват.
- Получените мейли могат да се разпределят в папки автоматично, на базата на някакво *правило*. Всяко правило има име и дефиниция (повече детайли малко по-долу).
 
    > Например, всички асистенти в MJT курса имат правило, което мести всички мейли от Стойо със subject, който започва с "[MJT]" в папка `MJT`.

- Активните правила могат да са нула или повече и могат да бъдат променяни динамично - нов акакунт в mail client-а има нула активни правила - впоследствие се махат или добавят такива.
- Всички активни правила се изпълняват при получаване на нов мейл и се проверява, дали някое от тях може да се приложи върху мейла. Ако има две правила, които могат да се приложат, се изпълнява това с по-висок приоритет.
- При добавяне на ново правило, то се изпълнява върху всички мейли от папката `inbox`.
- Не е възможно добавяне на правило, което е *в конфликт* с някое вече съществуващо правило. *Конфликтни правила* наричаме правила, които имат еднаква дефиниция и еднакъв приоритет, но са предназначени за различни папки.

По-долу сме изброили задължителните класове и интерфейси, които трябва да съдържа решението ви. Свободни сте да добавяте свои класове и интерфейси, като не забравяте да следвате принципите за чист код.
Както винаги, дефинираните в условието интерфейси **не бива да бъдат променяни**.

### Задължителни интерфейси и класове

В пакета `bg.sofia.uni.fmi.mjt.mail` създайте клас `Outlook` с публичен конструктор по подразбиране, който имплементира интерфейса `MailClient`: 

```java
package bg.sofia.uni.fmi.mjt.mail;

import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.RuleAlreadyDefinedException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.FolderAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.FolderNotFoundException;
import bg.sofia.uni.fmi.mjt.mail.exceptions.InvalidPathException;

import java.util.Collection;

public interface MailClient {

    /**
     * Creates a new account in the MailClient
     *
     * @param accountName short name of the account
     * @param email       email of the account
     * @return the created Account
     * @throws IllegalArgumentException      if any of the string parameters is null, empty or blank
     * @throws AccountAlreadyExistsException if account with the same name is already present in the client
     */
    Account addNewAccount(String accountName, String email);

    /**
     * @param accountName name of the account for which the folder is created
     * @param path        full path to the folder. The root folder and the path separator character
     *                    is forward slash ('/')
     * @throws IllegalArgumentException     if any of the string parameters is null, empty or blank
     * @throws AccountNotFoundException     if the account is not present
     * @throws InvalidPathException         if the folder path does not start from the root folder
     *                                      of received mails, or if some intermediate folders do not exist
     * @throws FolderAlreadyExistsException if folder with the same absolute path is already present
     *                                      for the provided account
     */
    void createFolder(String accountName, String path);

    /**
     * Creates a new Rule for the current mail client.
     * The following definition is the valid format for rules:
     * subject-includes: <list-of-keywords>
     * subject-or-body-includes: <list-of-keywords>
     * recipients-includes: <list-of-recipient-emails>
     * from: <sender-email>
     * 
     * The order is not determined, and the list might not be full. Example:
     * subject-includes: mjt, izpit, 2022
     * subject-or-body-includes: izpit
     * from: stoyo@fmi.bg
     * 
     * For subject-includes and subject-or-body-includes rules, if more than one keywords is specified, all must
     * be contained for the rule to match, i.e. it is a conjunction condition. For recipients-includes,
     * it's enough for one listed recipient to match (disjunction condition). For from, it should be exact match.
     *
     * @param accountName    name of the account for which the rule is applied
     * @param folderPath     full path of the destination folder
     * @param ruleDefinition string definition of the rule
     * @param priority       priority of the rule - [1,10], 1 = highest priority
     * @throws IllegalArgumentException    if any of the string parameters is null, empty or blank,
     *                                     or the priority of the rule is not within the expected range
     * @throws AccountNotFoundException    if the account does not exist
     * @throws FolderNotFoundException     if the folder does not exist
     * @throws RuleAlreadyDefinedException if the rule definition contains a rule/condition that already exists
     */
    void addRule(String accountName, String folderPath, String ruleDefinition, int priority);

    /**
     * The mail metadata has the following format (we always expect valid format of the mail metadata,
     * no validations are required):
     * sender: <sender-email>
     * subject: <subject>
     * recipients: <list-of-emails>
     * received: <LocalDateTime> - in format yyyy-MM-dd HH:mm
     * 
     * The order is not determined and the list might not be full. Example:
     * sender: testy@gmail.com
     * subject: Hello, MJT!
     * recipients: pesho@gmail.com, gosho@gmail.com,
     * received: 2022-12-08 14:14
     *
     * @param accountName  the recipient account
     * @param mailMetadata metadata, including the sender, all recipients, subject, and receiving time
     * @param mailContent  content of the mail
     * @throws IllegalArgumentException if any of the parameters is null, empty or blank
     * @throws AccountNotFoundException if the account does not exist
     * @throws FolderNotFoundException  if the folder does not exist
     */
    void receiveMail(String accountName, String mailMetadata, String mailContent);

    /**
     * Returns a collection of all mails in the provided folder
     *
     * @param account    name of the selected account
     * @param folderPath full path of the folder
     * @return collections of mails available in the folder
     * @throws IllegalArgumentException if any of the parameters is null, empty or blank
     * @throws AccountNotFoundException if the account does not exist
     * @throws FolderNotFoundException  if the folder does not exist
     */
    Collection<Mail> getMailsFromFolder(String account, String folderPath);

    /**
     * Sends an email. This stores the mail into the sender's "/sent" folder.
     * For each recipient in the recipients email list in the metadata, if an account with this email exists,
     * a {@code receiveMail()} for this account, mail metadata and mail content is called.
     * If an account with the specified email does not exist, it is ignored.
     *
     * @param accountName  name of the sender
     * @param mailMetadata metadata of the mail. "sender" field should be included automatically
     *                     if missing or not correctly set
     * @param mailContent  content of the mail
     * @throws IllegalArgumentException if any of the parameters is null, empty or blank
     */
    void sendMail(String accountName, String mailMetadata, String mailContent);

}
```

#### Record `Mail`

```java
package bg.sofia.uni.fmi.mjt.mail;

public record Mail(Account sender, Set<String> recipients, String subject, String body, LocalDateTime received) {
}
```

#### Record `Account`

```java
package bg.sofia.uni.fmi.mjt.mail;

public record Account(String emailAddress, String name) {
}
```

### ✒️ Бележки 

- :exclamation::exclamation: **Решения, използващи [Java Stream API](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/stream/package-summary.html), lambdas, и всичко останало, което не е покрито до момента, няма да се приемат за това домашно.**
- Rule дефинициите ще са представени като низ на няколко реда - ако някой тип условие липсва (например sender), това значи, че се приемат всякакви стойности и не влиза в проверката за правилото.


### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
└── bg.sofia.uni.fmi.mjt.mail
    ├── exceptions
    │   ├── AccountAlreadyExistsException.java
    │   ├── AccountNotFoundException.java
    │   ├── FolderAlreadyExistsException.java
    │   ├── FolderNotFoundException.java
    │   ├── InvalidPathException.java
    │   └── RuleAlreadyDefinedException.java
    ├── Account.java
    ├── Mail.java
    ├── MailClient.java
    └── Outlook.java
test
└─ bg.sofia.uni.fmi.mjt.mail
    └─ (...)
```

### Предаване

За да предадете решението си, архивирайте в **zip** архив `src` и `test` директориите на проекта и го качете в съответния assignment в грейдъра.

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност, и за автоматични тестове с добър code coverage (80% от оценката)
* добър обектно-ориентиран дизайн, спазване на правилата за чист код и подбиране на оптимални за задачата структури от данни (20% от оценката)

### Желаем ви успех! :four_leaf_clover: 
