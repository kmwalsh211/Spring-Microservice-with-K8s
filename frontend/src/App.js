import React, { useEffect, useState } from "react";
import axios from "axios";

function App() {
    const [movies, setMovies] = useState([]);
    const [title, setTitle] = useState("");
    const [year, setYear] = useState("");
    const [editId, setEditId] = useState(null);
    const [editTitle, setEditTitle] = useState("");
    const [editYear, setEditYear] = useState("");
    const [error, setError] = useState("");

    // Load all movies on start
    useEffect(() => {
        fetchMovies();
    }, []);

    const fetchMovies = () => {
        axios
            .get("/api/viewAllMovies")
            .then((res) => setMovies(res.data))
            .catch((err) => console.error(err));
    };

    const addMovie = (e) => {
        e.preventDefault();
        setError("");

        axios
            .post("/api/add", {
                title: title,
                year: parseInt(year),
            })
            .then(() => {
                setTitle("");
                setYear("");
                fetchMovies();
            })
            .catch((err) => {
                console.error(err);
                setError("Failed to add movie. Maybe it already exists?");
            });
    };

    const deleteMovie = (id) => {
        axios
            .delete("/api/deleteMovieById", { params: { id } })
            .then(() => fetchMovies())
            .catch((err) => console.error(err));
    };

    const startEdit = (movie) => {
        setEditId(movie.id);
        setEditTitle(movie.title);
        setEditYear(movie.year);
    };

    const updateMovie = (e) => {
        e.preventDefault();
        axios
            .put("/api/updateTitleById", null, {
                params: { id: editId, title: editTitle },
            })
            .then(() =>
                axios.put("/api/updateYearById", null, {
                    params: { id: editId, year: parseInt(editYear) },
                })
            )
            .then(() => {
                setEditId(null);
                setEditTitle("");
                setEditYear("");
                fetchMovies();
            })
            .catch((err) => console.error(err));
    };

    return (
        <div style={{ padding: "2rem", fontFamily: "Arial, sans-serif" }}>
            <h1>🎬 Movie Database</h1>

            {/* Add Movie */}
            <form onSubmit={addMovie} style={{ marginBottom: "2rem" }}>
                <h2>Add Movie</h2>
                <input
                    type="text"
                    placeholder="Title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                    style={{ marginRight: "1rem" }}
                />
                <input
                    type="number"
                    placeholder="Year"
                    value={year}
                    onChange={(e) => setYear(e.target.value)}
                    required
                    style={{ marginRight: "1rem" }}
                />
                <button type="submit">Add</button>
                {error && <p style={{ color: "red" }}>{error}</p>}
            </form>

            {/* Edit Movie */}
            {editId && (
                <form onSubmit={updateMovie} style={{ marginBottom: "2rem" }}>
                    <h2>Edit Movie ID: {editId}</h2>
                    <input
                        type="text"
                        value={editTitle}
                        onChange={(e) => setEditTitle(e.target.value)}
                        style={{ marginRight: "1rem" }}
                    />
                    <input
                        type="number"
                        value={editYear}
                        onChange={(e) => setEditYear(e.target.value)}
                        style={{ marginRight: "1rem" }}
                    />
                    <button type="submit">Update</button>
                    <button
                        type="button"
                        onClick={() => setEditId(null)}
                        style={{ marginLeft: "1rem" }}
                    >
                        Cancel
                    </button>
                </form>
            )}

            {/* Movie List */}
            <h2>All Movies</h2>
            {movies.length === 0 ? (
                <p>No movies found.</p>
            ) : (
                <table
                    border="1"
                    cellPadding="10"
                    style={{ borderCollapse: "collapse", width: "100%" }}
                >
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Year</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {movies.map((movie) => (
                        <tr key={movie.id}>
                            <td>{movie.id}</td>
                            <td>{movie.title}</td>
                            <td>{movie.year}</td>
                            <td>
                                <button onClick={() => deleteMovie(movie.id)}>Delete</button>
                                <button
                                    onClick={() => startEdit(movie)}
                                    style={{ marginLeft: "1rem" }}
                                >
                                    Edit
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default App;
