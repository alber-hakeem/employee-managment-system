--liquibase formatted sql

--changeset alber:20221102_create_locale_message_table_and_seq
CREATE SEQUENCE IF NOT EXISTS public.locale_message_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.locale_message_id_seq
    OWNER TO ${user_owner};

CREATE TABLE IF NOT EXISTS public.locale_message
(
    id                bigint                                              NOT NULL DEFAULT nextval('locale_message_id_seq'::regclass),
    code              character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lang              character varying(255) COLLATE pg_catalog."default" NOT NULL,
    message           character varying(255) COLLATE pg_catalog."default" NOT NULL,
    module            character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type              character varying(100) COLLATE pg_catalog."default" NOT NULL,
    icon_url          character varying(255) COLLATE pg_catalog."default",
    created_date      timestamp,
    modified_date     timestamp,
    created_by        character varying(100),
    modified_by       character varying(100),
    marked_as_deleted boolean                                                      default false,
    CONSTRAINT locale_message_pkey PRIMARY KEY (id),
    CONSTRAINT uk91e05u39jday1nr3pt398pr3o UNIQUE (module, code, lang)
)
    TABLESPACE pg_default;

ALTER TABLE public.locale_message
    OWNER TO ${user_owner};

--rollback drop table public.locale_message
--rollback drop sequence public.locale_message_id_seq