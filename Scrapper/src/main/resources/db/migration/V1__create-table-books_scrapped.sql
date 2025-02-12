CREATE TABLE books_scrapped (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    price INTEGER NOT NULL,
    link TEXT NOT NULL
);