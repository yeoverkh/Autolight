package ua.yehor.autolightbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a device entity used within the system.
 * This class defines attributes and behaviors of a device.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DeviceEntity {
    /**
     * Unique identifier for the device entity.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of the device.
     */
    private String name;

    /**
     * The user associated with this device.
     * An instance of UserEntity.
     */
    @ManyToOne
    @JsonIgnore
    private UserEntity user;

    /**
     * Set of readings associated with this device.
     * These readings are related to this device.
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReadingEntity> readings;

    /**
     * Set of lamps associated with this device.
     * These lamps are related to this device.
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LampEntity> lamps;

    /**
     * Constructs a DeviceEntity object with a specified name.
     *
     * @param name The name of the device.
     */
    public DeviceEntity(String name) {
        this.name = name;
        this.readings = Set.of();
        this.lamps = Set.of();
    }

    /**
     * Adds a reading entity to the set of readings associated with this device.
     *
     * @param reading The ReadingEntity to be added.
     */
    public void addReading(ReadingEntity reading) {
        readings.add(reading);
    }

    public void addLamp(LampEntity lamp) {
        lamps.add(lamp);
    }

    public void removeLamp(LampEntity lamp) {
        lamps.remove(lamp);
    }
}
