package epam.by.ivanchenko.model;

import jakarta.persistence.*;                      // javax ?
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 45, message = "Имя должно быть от 2 до 45 символов")
    private String username;

    @Column(name = "year")
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int year;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Person() {

    }

    public Person(String username, int year) {
        this.username = username;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", year=" + year +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (year != person.year) return false;
        if (username != null ? !username.equals(person.username) : person.username != null) return false;
        return password != null ? password.equals(person.password) : person.password == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
