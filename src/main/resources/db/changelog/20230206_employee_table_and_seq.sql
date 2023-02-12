--liquibase formatted sql

--changeset alber:20230206_create_employee_id_seq_table_and_seq
CREATE SEQUENCE IF NOT EXISTS public.employee_id_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.employee_id_sequence
    OWNER TO ${user_owner};

CREATE TABLE IF NOT EXISTS public.employee
(
    id                bigint                 NOT NULL DEFAULT nextval('employee_id_sequence'::regclass),
    first_name        character varying(100) NOT NULL,
    last_name         character varying(100) NOT NULL,
    status            TEXT                   NULL,
    events            TEXT                   NULL,
    age               integer                NOT NULL,

    created_date      timestamp,
    modified_date     timestamp,
    created_by        character varying(100),
    modified_by       character varying(100),
    marked_as_deleted boolean                         default false,

    CONSTRAINT drone_pkey PRIMARY KEY (id)
) TABLESPACE pg_default;

ALTER TABLE public.employee
    OWNER TO ${user_owner};

--rollback drop table public.employee
--rollback drop sequence public.employee_id_sequence