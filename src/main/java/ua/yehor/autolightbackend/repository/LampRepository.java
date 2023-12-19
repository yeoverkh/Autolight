package ua.yehor.autolightbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yehor.autolightbackend.model.LampEntity;

import java.util.List;

/**
 * Repository interface for LampEntity to perform CRUD operations.
 */
@Repository
public interface LampRepository extends JpaRepository<LampEntity, Long> {
    /**
     * Retrieves a lamp by its name.
     *
     * @param name The name of the lamp to retrieve.
     * @return The LampEntity with the specified name.
     */
    LampEntity findByName(String name);

    /**
     * Retrieves all lamps associated with a specific device ID.
     *
     * @param deviceId The ID of the device to retrieve lamps for.
     * @return A list of LampEntity objects associated with the specified device ID.
     */
    List<LampEntity> findAllByDeviceId(Long deviceId);
}
