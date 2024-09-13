-- public.transactions definition


-- Drop the customers table if it exists
DROP TABLE IF EXISTS public.customers;

CREATE TABLE public.customers (
	id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
	full_name varchar NULL,
	CONSTRAINT customers_pk PRIMARY KEY (id)
);


-- Drop the transactions table if it exists
DROP TABLE IF EXISTS public.transactions;

CREATE TABLE public.transactions (
	id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
	"type" VARCHAR(20) NULL,
     from_account BIGINT NULL,
        from_acc_sort_code INT NULL,
        to_account BIGINT NULL,
        to_acc_sort_code INT NULL,
        amount DECIMAL NOT NULL,
        CONSTRAINT transactions_pk PRIMARY KEY (id),
        CONSTRAINT transactions_accounts_fk FOREIGN KEY (from_account)
            REFERENCES public.accounts ("number"),
        CONSTRAINT transactions_accounts_fk_1 FOREIGN KEY (to_account)
            REFERENCES public.accounts ("number")
);

-- Drop the accounts table if it exists
DROP TABLE IF EXISTS public.accounts;

CREATE TABLE public.accounts (
	"number" bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
	sort_code int4  DEFAULT 4789 NOT NULL,
	"name" varchar(255) NOT NULL,
	opening_balance decimal NOT NULL,
	transaction_id bigint NULL,
	balance decimal NULL,
	customer_id bigint NOT NULL,
	CONSTRAINT accounts_pk PRIMARY KEY ("number"),
	CONSTRAINT accounts_customers_fk FOREIGN KEY (customer_id) REFERENCES public.customers(id),
	CONSTRAINT accounts_transactions_fk FOREIGN KEY (transaction_id) REFERENCES public.transactions(id)
);

