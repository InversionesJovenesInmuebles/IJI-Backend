-- Definir claves foráneas en Agente
ALTER TABLE Agente
    ADD CONSTRAINT fk_inmobiliaria
        FOREIGN KEY (id_inmobiliaria)
            REFERENCES Inmobiliaria(id_inmobiliaria);

-- Definir claves foráneas en Inmobiliaria
ALTER TABLE Inmobiliaria
    ADD CONSTRAINT fk_agente_inmobiliaria
        FOREIGN KEY (id_agente)
            REFERENCES Agente(id_agente);

-- Definir claves foráneas en Propiedad
ALTER TABLE Propiedad
    ADD CONSTRAINT fk_agente_propiedad
        FOREIGN KEY (id_agente)
            REFERENCES Agente(id_agente);

-- Definir claves foráneas en Foto
ALTER TABLE Foto
    ADD CONSTRAINT fk_propiedad_foto
        FOREIGN KEY (id_propiedad)
            REFERENCES Propiedad(id_propiedad);

-- Definir claves foráneas en Departamento
ALTER TABLE Departamento
    ADD CONSTRAINT fk_propiedad_departamento
        FOREIGN KEY (id_propiedad)
            REFERENCES Propiedad(id_propiedad),
ADD CONSTRAINT fk_agente_departamento
FOREIGN KEY (id_agente)
REFERENCES Agente(id_agente);

-- Definir claves foráneas en Casa
ALTER TABLE Casa
    ADD CONSTRAINT fk_propiedad_casa
        FOREIGN KEY (id_propiedad)
            REFERENCES Propiedad(id_propiedad),
ADD CONSTRAINT fk_agente_casa
FOREIGN KEY (id_agente)
REFERENCES Agente(id_agente);
