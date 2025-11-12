# Домашно 1

## JobMatch 💼

`Краен срок: 26.11.2025 23:59`

### Описание на задачата

> HR: "Търсим junior developer с 5 години опит"  
> Също HR: "Защо никой не кандидатства?" 🤔

В света на tech recruitment-а, перфектният match е като нетоксична игра на League of Legends - изключителна рядкост!

Ще създадем интелигентна платформа за търсене на работа, която използва алгоритми за съвпадение и анализ, за да свърже кандидатите с идеалните за тях позиции, подобно на [LinkedIn](linkedin.com).

Платформата трябва да поддържа:
- Регистрация на кандидати и работодатели
- Публикуване на обяви
- Търсене на най-подходящи кандидати/обяви по зададена стратегия
- Анализ на сходни профили и липсващи умения

### JobMatchAPI

В пакета `bg.sofia.uni.fmi.mjt.jobmatch` създайте клас `JobMatch` с публичен конструктор по подразбиране, който имплементира интерфейса `JobMatchAPI`:

```java
package bg.sofia.uni.fmi.mjt.jobmatch;

import bg.sofia.uni.fmi.mjt.jobmatch.matching.SimilarityStrategy;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Candidate;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateJobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.CandidateSimilarityMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Employer;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.JobPosting;
import bg.sofia.uni.fmi.mjt.jobmatch.model.PlatformStatistics;
import bg.sofia.uni.fmi.mjt.jobmatch.model.match.SkillRecommendation;

import java.util.List;

public interface JobMatchAPI {

    /**
     * Registers a new candidate in the system.
     *
     * @param candidate The candidate to register
     * @return The registered candidate (same instance that was passed in)
     * @throws IllegalArgumentException   if candidate is null
     * @throws UserAlreadyExistsException if a candidate with the same email already exists
     */
    Candidate registerCandidate(Candidate candidate);

    /**
     * Registers a new employer in the system.
     *
     * @param employer The employer to register
     * @return The registered employer (same instance that was passed in)
     * @throws IllegalArgumentException   if employer is null
     * @throws UserAlreadyExistsException if an employer with the same email already exists
     */
    Employer registerEmployer(Employer employer);

    /**
     * Posts a new job posting in the system.
     *
     * @param jobPosting The job posting to publish
     * @return The published job posting (same instance that was passed in)
     * @throws IllegalArgumentException if jobPosting is null
     * @throws UserNotFoundException    if the employer publishing the job posting is not registered
     */
    JobPosting postJobPosting(JobPosting jobPosting);

    /**
     * Finds the top N candidates that best match a given job posting.
     * Candidates with zero similarity are not included in the result.
     * The matching is based on the similarity between the candidate's skills
     * and the job requirements, calculated using the provided strategy.
     * <p>
     * Results are sorted by:
     * 1. Similarity score in descending order (higher similarity first)
     * 2. If scores are equal, by candidate name in alphabetical order (case-sensitive)
     *
     * @param jobPostingId The ID of the job posting
     * @param limit        The maximum number of candidates to return
     * @param strategy     The similarity calculation strategy to use
     * @return An unmodifiable list of CandidateJobMatch objects, sorted as described above.
     * If there are fewer than 'limit' candidates, return all of them.
     * If there are no candidates with non-zero similarity, return an empty list.
     * @throws IllegalArgumentException    if jobPostingId is null, empty or blank, limit is non-positive, or strategy is null
     * @throws JobPostingNotFoundException if no job posting with this ID exists
     */
    List<CandidateJobMatch> findTopNCandidatesForJob(String jobPostingId, int limit, SimilarityStrategy strategy);

    /**
     * Finds the top N job postings that best match a given candidate.
     * Job postings with zero similarity are not included in the result.
     * The matching is based on the similarity between the job requirements and the candidate's skills,
     * calculated using the provided strategy.
     * <p>
     * Results are sorted by:
     * 1. Similarity score in descending order (higher similarity first)
     * 2. If scores are equal, by job title in alphabetical order (case-sensitive)
     *
     * @param candidateEmail The email of the candidate
     * @param limit          The maximum number of jobs to return
     * @param strategy       The similarity calculation strategy to use
     * @return An unmodifiable list of CandidateJobMatch objects, sorted as described above.
     * If there are fewer than 'limit' jobs, return all of them.
     * If there are no jobs with non-zero similarity, return an empty list.
     * @throws IllegalArgumentException   if candidateEmail is null or blank, limit is non-positive, or strategy is null
     * @throws CandidateNotFoundException if no candidate with this email exists
     */
    List<CandidateJobMatch> findTopNJobsForCandidate(String candidateEmail, int limit, SimilarityStrategy strategy);

    /**
     * Finds candidates with similar professional profiles based on skills similarity.
     * This is analogous to LinkedIn's "People also viewed" or "People similar to this profile" feature.
     * <p>
     * The method calculates skill similarity between the given candidate and all other candidates
     * using the provided strategy. Results are sorted by:
     * 1. Similarity score in descending order
     * 2. If scores are equal, by candidate name in alphabetical order (case-sensitive)
     * Candidates with zero similarity are not included in the result.
     *
     * @param candidateEmail The email of the candidate
     * @param limit          The maximum number of similar candidates to return
     * @param strategy       The similarity calculation strategy to use
     * @return An unmodifiable list of CandidateSimilarityMatch objects representing similar candidates,
     * sorted as described above. The given candidate is NOT included in the results.
     * If there are fewer than 'limit' similar candidates, return all of them.
     * If there are no other candidates, return an empty list.
     * @throws IllegalArgumentException   if candidateEmail is null or blank, limit is non-positive, or strategy is null
     * @throws CandidateNotFoundException if no candidate with this email exists
     */
    List<CandidateSimilarityMatch> findSimilarCandidates(String candidateEmail, int limit, SimilarityStrategy strategy);

    /**
     * Provides intelligent skill recommendations for a candidate to improve their job match scores.
     * <p>
     * This method analyzes ALL job postings in the system.
     * <p>
     * The algorithm works as follows:
     * <p>
     * 1. For each job posting, calculate current similarity score with the candidate
     * 2. For each skill the candidate is MISSING (present in job but not in candidate profile):
     * - Temporarily add that skill to candidate's profile with level equal to required level
     * - Recalculate similarity score
     * - Calculate improvement: new_score - old_score
     * 3. Aggregate (sum up) improvements across all job postings for each missing skill
     * 4. Return top N skills ranked by total improvement potential
     * <p>
     * Results are sorted by:
     * 1. Total improvement score in descending order (highest impact first)
     * 2. If improvement scores are equal, by skill name alphabetically (case-sensitive)
     * <p>
     * Example:
     * - Candidate has: {Java:4, Python:3}
     * - Job1 requires: {Java:5, Python:4, AWS:3} - similarity: 0.85
     * - Job2 requires: {Java:4, AWS:4, Docker:3} - similarity: 0.70
     * <p>
     * Missing skills analysis:
     * - Adding AWS:3 to candidate → Job1 similarity becomes 0.92 (improvement: 0.07)
     * - Adding AWS:4 to candidate → Job2 similarity becomes 0.88 (improvement: 0.18)
     * - Total AWS improvement: 0.25
     * <p>
     * - Adding Docker:3 to candidate → Job2 similarity becomes 0.85 (improvement: 0.15)
     * - Total Docker improvement: 0.15
     * <p>
     * Result: [SkillRecommendation(AWS, 0.25), SkillRecommendation(Docker, 0.15)]
     * <p>
     * IMPLEMENTATION NOTE:
     * The platform's default similarity strategy is Cosine Similarity (considers skill levels).
     *
     * @param candidateEmail The email of the candidate
     * @param limit          The maximum number of skill recommendations to return
     * @return An unmodifiable list of SkillRecommendation objects, sorted as described above.
     * If there are no missing skills across all job postings, return an empty list.
     * If there are fewer than 'limit' missing skills, return all of them.
     * @throws IllegalArgumentException   if candidateEmail is null, empty or blank or limit is non-positive
     * @throws CandidateNotFoundException if no candidate with this email exists
     */
    List<SkillRecommendation> getSkillRecommendationsForCandidate(String candidateEmail, int limit);

    /**
     * Returns comprehensive statistics about the platform.
     * - totalCandidates: the total number of registered candidates
     * - totalEmployers: the total number of registered employers
     * - totalJobPostings: the total number of posted job postings
     * - mostCommonSkillName: the name of the skill that appears most frequently across all candidates.
     * In case of a tie, return the skill name that comes first alphabetically (case-sensitive).
     * If there are no candidates, return null.
     * - highestPaidJobTitle: the title of the job posting with the highest salary.
     * In case of a tie, return the job title that comes first alphabetically (case-sensitive).
     * If there are no job postings, return null.
     *
     * @return A PlatformStatistics object containing various metrics
     */
    PlatformStatistics getPlatformStatistics();

}
```

### Помощни типове

Създайте следните типове в пакета `bg.sofia.uni.fmi.mjt.jobmatch.model`.

| Тип | Конструктор | Описание | Record |
|-----|-------------|----------|--------|
| `Skill` | `Skill(String name, int level)` | Умение (0 = no experience, 5 = expert) | да |
| `Candidate` | `Candidate(String name, String email, Set<Skill> skills, Education education, int yearsOfExperience)` | Кандидат за работа | не |
| `Employer` | `Employer(String companyName, String email)` | Работодател (компания) | да |
| `JobPosting` | `JobPosting(String id, String title, String employerEmail, Set<Skill> requiredSkills, Education requiredEducation, int requiredYearsOfExperience, double salary)` | Обява за работа | не |
| `CandidateJobMatch` | `CandidateJobMatch(Candidate candidate, JobPosting jobPosting, double similarityScore)` | Съвпадение кандидат-позиция | не |
| `CandidateSimilarityMatch` | `CandidateSimilarityMatch(Candidate targetCandidate, Candidate similarCandidate, double similarityScore)` | Профил на подобен кандидат | не |
| `SkillRecommendation` | `SkillRecommendation(String skillName, double improvementScore)` | Препоръка за умение | да |
| `PlatformStatistics` | `PlatformStatistics(int totalCandidates, int totalEmployers, int totalJobPostings, String mostCommonSkillName, String highestPaidJobTitle)` | Статистики на платформата | да |

#### Общи изисквания за всички гореизброени типове:

**✅ Валидация в конструктора:**

- `String` параметри: не null, не blank
- `Set` параметри: не null, не empty
- Числа (години, заплата): неотрицателни
- `similarityScore`: в интервала [0, 1]
- `improvementScore`: неотрицателен (>= 0)

#### `Education`

Образователното ниво се моделира от enum `Education`:

```java
package bg.sofia.uni.fmi.mjt.jobmatch.model;

public enum Education {
    HIGH_SCHOOL(1),
    BACHELORS(2),
    MASTERS(3),
    PHD(4);
    
    private final int level;
    
    Education(int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }
}
```

### Алгоритми за сходство

Системата поддържа два алгоритъма за изчисляване на сходство между умения и те имплементират следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.jobmatch.matching;

import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.Skill;

import java.util.Set;

/**
 * Strategy interface for calculating similarity between skill sets.
 *
 * Different implementations use different algorithms to measure how well
 * a candidate's skills match job requirements. The strategy is stateless
 * and can be passed as a parameter to methods that need it.
 *
 * This follows the Strategy Pattern - see:
 * https://refactoring.guru/design-patterns/strategy
 */
public interface SimilarityStrategy {

    /**
     * Calculates similarity score between two skill sets.
     *
     * @param candidateSkills The skills possessed by a candidate
     * @param jobSkills The skills required by a job
     * @return Similarity score in range [0, 1], where 1 means perfect match and 0 means no match
     * @throws IllegalArgumentException if either parameter is null
     */
    double calculateSimilarity(Set<Skill> candidateSkills, Set<Skill> jobSkills);

}
```

#### Jaccard Similarity (подход, основан на множества)

Jaccard Similarity е проста метрика, която измерва припокриването между две множества умения, игнорирайки нивата на владеене на уменията.

**Формула:**

```
J(A, B) = |A ∩ B| / |A ∪ B|
```

където:
- `A ∩ B` е **сечението** на уменията
- `A ∪ B` е **обединението** на уменията

**Пример:**

```
Candidate skills (по име): {Java, Python, SQL}
Job requirements (по име): {Java, SQL, AWS}

Intersection: {Java, SQL} → размер = 2
Union: {Java, Python, SQL, AWS} → размер = 4
Jaccard Similarity = 2 / 4 = 0.5
```

В пакета `bg.sofia.uni.fmi.mjt.jobmatch.matching` създайте клас `JaccardSimilarity` с публичен конструктор по подразбиране, който имплементира `SimilarityStrategy`.

**Corner case:** Ако и двете множества са празни, дефинираме Jaccard = 0

**Ресурси:**
- [Jaccard Index - Wikipedia](https://en.wikipedia.org/wiki/Jaccard_index)
- [Jaccard Similarity - GeeksforGeeks](https://www.geeksforgeeks.org/jaccard-similarity/)

#### Cosine Similarity (подход, основан на вектори)

Cosine Similarity е по-сложна метрика, която взема предвид не само присъствието на умения, но и техните нива на владеене (0-5).

1. **Концепция:** Всеки кандидат и позиция се представят като вектор, където всяко умение е измерение. Стойността на умението е неговото ниво (0-5). Ако умението липсва, стойността е 0. За да определим еднозначно реда на уменията във вектора, ще ги подреждаме по азбучен ред, case-sensitive.

   Пример:
   - Candidate skills: {Java: 4, Python: 3, SQL: 2}  
   - Job requirements: {Java: 5, Python: 2, JavaScript: 3}
   
   Обединени умения (sorted):
    - [Java, JavaScript, Python, SQL]
   
   Candidate vector: [4, 0, 3, 2]  
   Job vector: [5, 3, 2, 0]

2. **Изчисляване:**

   ```
   cosine similarity = (A · B) / (||A|| × ||B||)
   ```

   Където:
   - `A · B` = скаларно произведение (dot product)
   - `||A||`, `||B||` = магнитуди (normes)

   За примера:

   ```
   Dot product: 4×5 + 0×3 + 3×2 + 2×0 = 26
   ||A|| = sqrt(4² + 0² + 3² + 2²) = sqrt(29) ≈ 5.385
   ||B|| = sqrt(5² + 3² + 2² + 0²) = sqrt(38) ≈ 6.164
   Similarity = 26 / (5.385 × 6.164) ≈ 0.783
   ```

Класът `CosineSimilarity` в пакета `bg.sofia.uni.fmi.mjt.jobmatch.matching` също има публичен конструктор по подразбиране.

**Corner case:** Ако и двата вектора са нулеви → similarity = 0

**Ресурси:**
- [Cosine Similarity - Wikipedia](https://en.wikipedia.org/wiki/Cosine_similarity)
- [Understanding Cosine Similarity](https://www.machinelearningplus.com/nlp/cosine-similarity/)

#### Skill Gap Analysis 🎯

*a.k.a. 'Какво още трябва да науча, за да не живея в мазето на родителите си?' 🏠*

За всеки кандидат намерете липсващите му умения спрямо обявите за работа и изчислете с кои нови умения би се подобрило най-много сходството му с тях.
Резултатът е списък от `SkillRecommendation`-и, сортирани по подобрение (в намаляващ ред), а при равенство - по име на умение (азбучен ред).
Подобрението за дадено умение се изчислява като сумата от разликите между новото и старото сходство с всички обяви.

**Пример:**

```
Candidate: {Java:4, Python:3}

Job1: {Java:5, Python:4, AWS:3} → current: 0.85
Job2: {Java:4, AWS:4, Docker:3} → current: 0.70
Job3: {Python:5, Docker:2} → current: 0.90

AWS: max level = 4
  + Job1: 0.92 - 0.85 = 0.07
  + Job2: 0.88 - 0.70 = 0.18
  + Job3: 0.90 - 0.90 = 0.00
  Total: 0.25

Docker: max level = 3
  + Job1: 0.85 - 0.85 = 0.00
  + Job2: 0.85 - 0.70 = 0.15
  + Job3: 0.95 - 0.90 = 0.05
  Total: 0.20

Result: [AWS (0.25), Docker (0.20)]
```

### Пакети

```
src/bg/sofia/uni/fmi/mjt/
└── jobmatch
    ├── exceptions
    │   ├── CandidateNotFoundException.java
    │   ├── JobPostingNotFoundException.java
    │   ├── UserAlreadyExistsException.java
    │   ├── UserNotFoundException.java
    │   └── (...)
    │
    ├── matching
    │   ├── CosineSimilarity.java
    │   ├── JaccardSimilarity.java
    │   ├── SimilarityStrategy.java
    │   └── (...)
    │
    ├── model
    │   ├── entity
    │   │   ├── Candidate.java
    │   │   ├── Education.java
    │   │   ├── Employer.java
    │   │   ├── JobPosting.java
    │   │   ├── Skill.java
    │   │   └── (...)
    │   │
    │   ├── match
    │   │   ├── CandidateJobMatch.java
    │   │   ├── CandidateSimilarityMatch.java
    │   │   ├── SkillRecommendation.java
    │   │   └── (...)
    │   │
    │   ├── PlatformStatistics.java
    │   └── (...)
    │
    ├── api
    │   └── JobMatchAPI.java
    │
    └── JobMatch.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- :exclamation: **Решения,
  използващи [Java Stream API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/package-summary.html),
  [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html), и всичко останало, което *не* е
  учено до момента в курса, няма да се приемат за това домашно.**

### Предаване

За да предадете решението си, качете цялата `src` директория на проекта в съответния assignment в грейдъра
(или я архивирайте в **zip** файл и качете него).

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност: чрез автоматични тестове (80% от оценката)
* добър обектно-ориентиран дизайн, спазване на правилата за чист код и подбиране на оптимални за задачата структури от
  данни (20% от оценката)

Обърнете внимание, че при качване на решението ви, в грейдъра ще се изпълни само _smoke_ тест, чиято цел е да изчистите
евентуални проблеми с компилацията. Референтите тестове и Checkstyle статичният код анализ ще се изпълнят еднократно
след изтичане на крайния срок за предаване. За функционалната коректност и качеството на кода ще трябва да се погрижите
без тяхната помощ.

### 🤖 Отговорно използване на AI и академична почтеност

Използването на генеративни AI инструменти (като GitHub Copilot, ChatGPT и др.) е допустимо единствено с цел подпомагане на процеса на учене, но не и като заместител на самостоятелното мислене и работа. Всеки студент носи пълна отговорност за разбирането, тестването и обяснението на кода, който предава. Представянето на код, който е очевидно автоматично генериран или който не можете да обясните и защитите устно или писмено, ще се счита за форма на недопустимо подпомагане или плагиатство, съгласно правилата на курса и университета. Ако сте използвали AI, посочете това в документацията – уточнете кои части са генерирани, с каква цел, и опишете накратко как работят и как сте проверили тяхната коректност, по същия начин, по който бихте цитирали външен източник. Целта на тази политика е да насърчи отговорната и критична употреба на съвременни инструменти, задълбоченото разбиране на материала и поддържането на високи стандарти на академична почтеност.

### Желаем ви успех! :four_leaf_clover:

P.S. Не забравяйте да спите. Сериозно. Кафе ≠ сън. ☕≠💤
