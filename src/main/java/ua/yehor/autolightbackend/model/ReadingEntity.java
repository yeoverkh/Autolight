package ua.yehor.autolightbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a reading entity captured by a device.
 * This class defines attributes and methods related to readings.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReadingEntity {
    /**
     * Unique identifier for the reading entity.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The device associated with this reading.
     */
    @ManyToOne
    @JsonIgnore
    private DeviceEntity device;

    /**
     * Name associated with the reading.
     */
    private String name;

    /**
     * Value of the reading.
     */
    private Integer value;

    /**
     * Date and time when the reading was captured.
     */
    private LocalDateTime dateTime;

    /**
     * Indicates whether the reading is a warning.
     */
    private Boolean isWarning;

    /**
     * Constructs a ReadingEntity object with specified parameters.
     *
     * @param name      The name associated with the reading.
     * @param value     The value of the reading.
     * @param isWarning Indicates whether the reading is a warning.
     */
    public ReadingEntity(String name, Integer value, Boolean isWarning) {
        this.name = name;
        this.value = value;
        this.isWarning = isWarning;
        this.dateTime = LocalDateTime.now();
    }

    /**
     * Checks if the reading is a warning.
     *
     * @return True if the reading is a warning, false otherwise.
     */
    @JsonIgnore
    public boolean isWarning() {
        return isWarning;
    }
}
