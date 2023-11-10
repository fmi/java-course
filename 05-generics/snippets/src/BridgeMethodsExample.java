import java.util.HashMap;
import java.util.Map;

// Base class Entity
abstract class Entity {
    private final long id;

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

// Generic Storage class for Entity
class EntityStorage<T extends Entity> {
    private Map<Long, T> entities;

    public EntityStorage() {
        this.entities = new HashMap<>();
    }

    public T getEntity(long id) {
        return entities.get(id);
    }

    public void addEntity(T entity) {
        this.entities.put(entity.getId(), entity);
    }
}

// Specialized Storage class for Employee
class Employee extends Entity {
    private String name;

    public Employee(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Specialized Storage class for EmployeeStorage with bridge method
class EmployeeStorage extends EntityStorage<Employee> {
    public EmployeeStorage() {
        super();
    }

    // Additional methods specific to EmployeeStorage
    public String getEmployeeName(long id) {
        return getEntity(id).getName();
    }

    // Due to type erasure, the getEntity method in the EmployeeStorage class have erased signatures.
    // The compiler generates bridge methods in the EmployeeStorage class to ensure that the overridden methods
    // in the EntityStorage<Employee> interface adhere to the erasure rules.
    //
    // Bridge methods are synthetic methods added by the compiler to maintain polymorphism
    // and type safety in the presence of type erasure. They are not directly written
    // in the source code but are introduced during the compilation process.
    @Override
    public Employee getEntity(long id) {
        return super.getEntity(id);
    }
}

public class BridgeMethodsExample {
    public static void main(String[] args) {
        Employee employee = new Employee(1, "John Doe");

        EmployeeStorage employeeStorage = new EmployeeStorage();

        // Access specific method
        System.out.println("Employee Name: " + employeeStorage.getEmployeeName(1));

        // Access generic method through the bridge
        Entity genericEntity = employeeStorage.getEntity(1);
        System.out.println("Generic Entity ID: " + genericEntity.getId());
    }
}
