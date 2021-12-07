package Objects;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "dates_spends")
public class DateSpent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date date;
    @Column(name = "spent")
    private float spent;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;



    public DateSpent(Date date, float spent) {
        this.date = date;
        this.spent = spent;
    }

    public DateSpent() {

    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getSpent() {
        return spent;
    }

    public void setSpent(float spent) {
        this.spent = spent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addSpent(float sum) {
        spent += sum;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DateSpent dateSpent = (DateSpent) o;
//        return month.equals(dateSpent.month) && category.equals(dateSpent.category);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(month, category);
//    }
}
