CREATE TABLE escritor
(
    id       UUID PRIMARY KEY NOT NULL,
    name          VARCHAR(50) NOT NULL,
    idade         INTEGER,
    livro_id UUID,
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);

