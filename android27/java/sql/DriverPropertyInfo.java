package java.sql;

public class DriverPropertyInfo
{
    public String name;
    public String description;
    public boolean required;
    public String value;
    public String[] choices;
    
    public DriverPropertyInfo(final String name, final String value) {
        this.description = null;
        this.required = false;
        this.value = null;
        this.choices = null;
        this.name = name;
        this.value = value;
    }
}
