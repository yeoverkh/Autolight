package ua.yehor.autolightbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yehor.autolightbackend.model.DeviceEntity;
import ua.yehor.autolightbackend.model.UserEntity;

import java.util.List;

/**
 * Repository interface for managing DeviceEntity objects in the database.
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    /**
     * Finds all devices associated with a specific user by their login.
     *
     * @param userLogin The login name of the user.
     * @return A list of DeviceEntity objects associated with the user.
     */
    List<DeviceEntity> findAllByUserLogin(String userLogin);

    /**
     * Finds a device by its name and associated user.
     *
     * @param name The name of the device.
     * @param user The associated UserEntity.
     * @return The DeviceEntity with the specified name and user.
     */
    DeviceEntity findByNameAndUser(String name, UserEntity user);
}
