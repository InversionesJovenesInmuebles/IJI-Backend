-- Crear tabla Cliente
CREATE TABLE Cliente (
    id_cliente BIGINT DEFAULT nextval('cliente_sequence') PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Crear tabla Inmobiliaria
CREATE TABLE Inmobiliaria (
    id_inmobiliaria BIGINT DEFAULT nextval('inmobiliaria_sequence') PRIMARY KEY,
    nombre_inmobiliaria VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono_contacto VARCHAR(20) NOT NULL,
    ruc VARCHAR(11) NOT NULL,
    role VARCHAR(20) NOT NULL,
    id_agente BIGINT
);

-- Crear tabla Agente
CREATE TABLE Agente (
    id_agente BIGINT DEFAULT nextval('agente_sequence') PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    nombre_inmobiliaria VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    id_inmobiliaria BIGINT
);

-- Crear tabla Propiedad
CREATE TABLE Propiedad (
    id_propiedad BIGINT DEFAULT nextval('propiedad_sequence') PRIMARY KEY,
    latitud VARCHAR(255),
    longitud VARCHAR(255),
    pais VARCHAR(255),
    region VARCHAR(255),
    provincia VARCHAR(255),
    distrito VARCHAR(255),
    direccion VARCHAR(255),
    descripcion TEXT,
    otras_comodidades TEXT,
    tipo_propiedad VARCHAR(255),
    area_terreno DOUBLE PRECISION,
    costo_total DOUBLE PRECISION,
    costo_inicial DOUBLE PRECISION,
    cochera BOOLEAN,
    cant_banos INT,
    cant_dormitorios INT,
    cant_cochera INT,
    id_agente BIGINT
);

-- Crear tabla Foto
CREATE TABLE Foto (
    id_foto BIGINT DEFAULT nextval('foto_sequence') PRIMARY KEY,
    nombre_foto VARCHAR(255),
    id_propiedad BIGINT NOT NULL
);

-- Crear tabla Departamento como subclase de Propiedad
CREATE TABLE Departamento (
    id_propiedad BIGINT PRIMARY KEY,
    pisos INT,
    interior INT,
    ascensor BOOLEAN,
    areas_comunes BOOLEAN,
    areas_comunes_especificas TEXT,
    id_agente BIGINT
);

-- Crear tabla Casa como subclase de Propiedad
CREATE TABLE Casa (
    id_propiedad BIGINT PRIMARY KEY,
    cant_pisos INT,
    area_jardin INT,
    jardin BOOLEAN,
    atico BOOLEAN,
    sotano BOOLEAN,
    id_agente BIGINT
);
