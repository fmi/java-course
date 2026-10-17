# Git: автентикация, клониране и обновяване

- [Автентикация от терминала](#автентикация-от-терминала)
    - [Вариант A: HTTPS + GitHub CLI ](#вариант-a-https--github-cli-най-лесно)
    - [Вариант B: Personal Access Token (PAT)](#вариант-b-personal-access-token-pat)
    - [SSH ключове (за напреднали)](#ssh-ключове-за-напреднали)
- [Клониране на репото локално](#клониране-на-репото-локално)
- [Обновяване с последните промени](#обновяване-с-последните-промени)
- [Git + IntelliJ IDEA](#git--intellij-idea)
- [Чести проблеми и решения](#чести-проблеми-и-решения)

---

## Автентикация от терминала

За да работите с репото от терминала, трябва да се автентикирате пред GitHub.
За курса препоръчваме **HTTPS** автентикация — през GitHub CLI или Personal Access Token (PAT). Тя е най-бърза за
настройка.

### Вариант A: HTTPS + GitHub CLI

1. Инсталирайте [GitHub CLI](https://cli.github.com/).
2. Логнете се:

   ```bash
   gh auth login
   ```

   Изберете `GitHub.com` → `HTTPS` → автентикация през браузър.

3. Настройте `git` да ползва `gh` за автентикация:

   ```bash
   gh auth setup-git
   ```

4. Проверете статуса:

   ```bash
   gh auth status
   ```

### Вариант B: Personal Access Token (PAT)

PAT е токен, който замества паролата при работа през HTTPS. Удобен е, когато не искате да инсталирате GitHub CLI.

GitHub предлага два вида токени — препоръчва се **fine-grained** токенът.

1. Отворете GitHub → **Settings → Developer settings → Personal access tokens → Fine-grained tokens → Generate new
   token**.
2. Задайте:
    - **Token name** — описателно име (напр. `java-course-laptop`).
    - **Expiration** — срок на валидност (препоръчително до 90 дни; не ползвайте „No expiration“).
    - **Repository access** — изберете репото (напр. само `fmi/java-course`), а не всички.
    - **Permissions** → **Repository permissions → Contents → Read and write** (достатъчно за `clone`, `pull` и `push`).
3. Натиснете **Generate token** и **веднага копирайте токена** — показва се само веднъж.
4. Клонирайте или дърпайте през HTTPS. Когато `git` поиска парола, поставете **токена вместо паролата**:

   ```bash
   git clone https://github.com/fmi/java-course.git
   # Username: вашият GitHub username
   # Password: <поставете PAT тук>
   ```

5. За да не въвеждате токена всеки път, кеширайте го в git credential helper:

    - **macOS:**

      ```bash
      git config --global credential.helper osxkeychain
      ```

    - **Linux:**

      ```bash
      git config --global credential.helper store
      ```

      > **Внимание:** `store` записва токена в чист текст в `~/.git-credentials`. На споделена машина ползвайте `cache`
      вместо `store`.

    - **Windows:** използва се вграденият `manager` (Git Credential Manager) по подразбиране.

> **Сигурност:** Третирайте PAT като парола. Не го качвайте в репо, не го споделяйте и не го пишете в код. Ако изтече,
> го отменете (**revoke**) от GitHub и генерирайте нов.

### SSH ключове (за напреднали)

SSH е **предпочитаният** начин за автентикация в по-дългосрочен план, защото:

- тайният ключ никога не напуска машината ви — при всяко действие изпращате само криптографски подпис, а не тайна през
  мрежата;
- няма срок на валидност за подновяване — настройвате го веднъж и работи с години;
- няма токен, който случайно да залепите в commit, лог или по време на screen share.

Това **не** означава, че PAT е несигурен. Напротив — един **fine-grained** PAT, ограничен до едно репо и с кратък срок,
е много добър избор и често е по-лесен за ограничаване. Разликата е в удобството: SSH изисква еднократна (понякога
объркваща) настройка, но после е безпроблемен; PAT се настройва лесно, но изисква подновяване при изтичане.

Понеже настройката на SSH ключове е по-сложна (генериране на ключ, `ssh-agent`, добавяне в GitHub, няколко акаунта),
няма да я описваме тук. Ако искате да я ползвате, следвайте официалното ръководство:

👉 [Connecting to GitHub with SSH](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)

---

## Клониране на репото локално

**През HTTPS** (препоръчано за курса):

```bash
git clone https://github.com/fmi/java-course.git
```

**През SSH** (ако сте настроили SSH ключ):

```bash
git clone git@github.com:fmi/java-course.git
```

Това създава папка `java-course` в текущата директория. Влезте в нея:

```bash
cd java-course
```

---

## Обновяване с последните промени

Репото се обновява често през семестъра. За да изтеглите последните промени:

```bash
git pull
```

Ако сте на различен branch и искате изрично да дръпнете от `master`:

```bash
git pull origin master
```

> **Бележка:** Ако имате локални промени, които са в конфликт, `git pull` ще ви предупреди. Запазете или скрийте
> промените си преди да дърпате:
>
> ```bash
> git stash        # скрива локалните промени
> git pull         # дърпа последните промени
> git stash pop    # връща скритите промени обратно
> ```

---

## Git + IntelliJ IDEA

IntelliJ IDEA има вградена поддръжка за Git, така че повечето действия (commit, push, pull, преглед на историята) може да правите от IDE-то, без терминал.

### Добавяне на GitHub акаунт

За да push-вате и pull-вате без да въвеждате парола всеки път, добавете GitHub акаунта си в IDE-то:

1. **File → Settings** (на macOS: **IntelliJ IDEA → Settings**) → **Version Control → GitHub**.
2. Натиснете **+** → **Log In with Token…** или **Log In via GitHub…**:
   - **Log In via GitHub…** — отваря браузър и ви автентикира автоматично (най-лесно).
   - **Log In with Token…** — поставете [Personal Access Token (PAT)](#вариант-b-personal-access-token-pat), ако предпочитате или ако browser login не сработи.
3. След успешен вход акаунтът ви се появява в списъка. Може да добавите повече от един акаунт и да зададете кой е по подразбиране.

> **Бележка:** IntelliJ ползва този акаунт само за операции през HTTPS и за GitHub интеграции (PR-и, issues). Ако сте клонирали репото през SSH, автентикацията минава през SSH ключа ви, а не през този акаунт.

### Настройки за Version Control

Полезни места в **File → Settings → Version Control**:

- **Git** — път до `git` изпълнимия файл (IDE-то обикновено го намира само), настройки за push/update и SSH executage (`Built-in` или `Native`).
- **Commit** — поведение при commit: напр. „Reformat code“, „Analyze code“ или „Optimize imports“ преди commit.
- **GitHub** — управление на свързаните акаунти (виж по-горе).

### Клониране на репото от IntelliJ

1. На стартовия екран: **Clone Repository** (или **File → New → Project from Version Control**).
2. Поставете URL-а на репото:
   - HTTPS: `https://github.com/fmi/java-course.git`
   - SSH: `git@github.com:fmi/java-course.git`
3. Изберете локална папка и натиснете **Clone**.

### Ежедневни операции през IDE-то

- **Pull (обновяване):** лентата с инструменти горе вдясно → иконата със стрелка надолу (**Update Project**, `Ctrl+T` / `⌘T`).
- **Commit:** **Commit** таб вляво или `Ctrl+K` / `⌘K`.
- **Push:** `Ctrl+Shift+K` / `⌘⇧K`.
- **История и промени:** долният **Git** панел (`Alt+9` / `⌘9`) показва log, branches и локални промени.

---

| Проблем                                                   | Причина / симптом                         | Решение                                                            |
|-----------------------------------------------------------|-------------------------------------------|--------------------------------------------------------------------|
| `could not read Username for 'https://github.com'`        | Няма настроена HTTPS автентикация         | Изпълнете `gh auth login` и `gh auth setup-git`, или ползвайте PAT |
| `remote: Invalid username or password` (при HTTPS)        | Въвели сте GitHub паролата вместо PAT     | Поставете Personal Access Token на мястото на паролата             |
| `remote: Support for password authentication was removed` | GitHub вече не приема пароли по HTTPS     | Използвайте PAT или GitHub CLI                                     |
| `Permission to <repo> denied to <user>`                   | Автентикирани сте с грешен акаунт         | Проверете с `gh auth status`; логнете се с правилния акаунт        |
| `fatal: not a git repository`                             | Не сте в папката на репото                | `cd` в клонираната директория                                      |
| Конфликти при `git pull`                                  | Локални промени се застъпват с отдалечени | Използвайте `git stash` преди `git pull`                           |
