import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchUsers, createUser, deleteUser } from "./redux/features/userSlice";
import { RootState, AppDispatch } from "./store";
import Navbar from "./components/navbar/Navbar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

const App: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const users = useSelector((state: RootState) => state.user.users);
  const status = useSelector((state: RootState) => state.user.status);
  const error = useSelector((state: RootState) => state.user.error);

  useEffect(() => {
    dispatch(fetchUsers());
  }, [dispatch]);

  const handleAddUser = () => {
    const newUser = {
      username: "Utilisateur Test",
      email: `user${Date.now()}@example.com`,
      password: "1234",
      role: "USER",
    };
    dispatch(createUser(newUser));
  };

  const handleDeleteUser = (id: string) => {
    dispatch(deleteUser(id));
  };

  return (
    <Router>
      <Navbar />
      <div className="container mx-auto p-4">
        <h1 className="text-2xl font-bold">Liste des utilisateurs</h1>
        {status === "loading" && <p>Chargement...</p>}
        {status === "failed" && <p>Erreur : {error}</p>}
        <button onClick={handleAddUser} className="bg-blue-500 text-white px-3 py-1 rounded">
          Ajouter un utilisateur
        </button>
        <ul>
          {users.map((user) => (
            <li key={user.id} className="flex justify-between items-center p-2 border-b">
              {user.username} - {user.email}
              <button onClick={() => handleDeleteUser(user.id!)} className="bg-red-500 text-white px-2 py-1 rounded">
                Supprimer
              </button>
            </li>
          ))}
        </ul>
      </div>
    </Router>
  );
};

export default App;