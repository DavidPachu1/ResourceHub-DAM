CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          rol ENUM('ADMIN', 'EMPLEADO', 'MANTENIMIENTO') NOT NULL DEFAULT 'EMPLEADO',
                          fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recursos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          tipo ENUM('SALA', 'VEHICULO', 'HARDWARE', 'OTROS') NOT NULL,
                          descripcion TEXT,
                          estado ENUM('DISPONIBLE', 'RESERVADO', 'MANTENIMIENTO', 'BAJA') NOT NULL DEFAULT 'DISPONIBLE',
                          imagen_url VARCHAR(255)
);

CREATE TABLE reservas (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          usuario_id BIGINT NOT NULL,
                          recurso_id BIGINT NOT NULL,
                          fecha_inicio DATETIME NOT NULL,
                          fecha_fin DATETIME NOT NULL,
                          observaciones TEXT,
                          CONSTRAINT fk_reserva_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                          CONSTRAINT fk_reserva_recurso FOREIGN KEY (recurso_id) REFERENCES recursos(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         recurso_id BIGINT NOT NULL,
                         creador_id BIGINT NOT NULL,
                         titulo VARCHAR(150) NOT NULL,
                         descripcion TEXT NOT NULL,
                         prioridad ENUM('BAJA', 'MEDIA', 'ALTA') DEFAULT 'MEDIA',
                         estado ENUM('ABIERTO', 'EN_PROCESO', 'RESUELTO') DEFAULT 'ABIERTO',
                         fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_ticket_recurso FOREIGN KEY (recurso_id) REFERENCES recursos(id) ON DELETE CASCADE,
                         CONSTRAINT fk_ticket_usuario FOREIGN KEY (creador_id) REFERENCES usuarios(id) ON DELETE CASCADE
);