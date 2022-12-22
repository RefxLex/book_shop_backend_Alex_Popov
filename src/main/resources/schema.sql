CREATE TABLE IF NOT EXISTS public.authors (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    deleted BOOL NOT NULL DEFAULT FALSE,
    UNIQUE (first_name, last_name)
);

CREATE TABLE IF NOT EXISTS public.book_categories (
    id SERIAL PRIMARY KEY,
    category_name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.books (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL UNIQUE,
    book_info TEXT NOT NULL,
    year INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    discount DOUBLE PRECISION NOT NULL,
    unit_in_stock INTEGER NOT NULL,
    author_id BIGINT NOT NULL REFERENCES authors (id),
    book_category_id BIGINT NOT NULL REFERENCES book_categories (id),
    hyperlink TEXT NOT NULL,
    removed BOOL NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.users (
    id SERIAL PRIMARY KEY,
    user_name TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    phone TEXT NOT NULL UNIQUE,
    role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.orders (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES users (id),
    country TEXT NOT NULL,
    city TEXT NOT NULL,
    street TEXT NOT NULL,
    apartment_number INTEGER NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    time_submitted TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS public.order_items (
    id SERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    amount INTEGER NOT NULL,
    sum DOUBLE PRECISION NOT NULL,
    order_id BIGINT NOT NULL REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS public.carts (
    id SERIAL PRIMARY KEY,
    total_price DOUBLE PRECISION NOT NULL,
    customer_id BIGINT NOT NULL UNIQUE REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS public.cart_items (
    id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    sum DOUBLE PRECISION NOT NULL,
    book_id BIGINT NOT NULL REFERENCES books (id),
    cart_id BIGINT NOT NULL REFERENCES carts (id),
    UNIQUE (book_id, cart_id)
);
