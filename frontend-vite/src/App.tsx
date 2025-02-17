import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchUsers, createUser, deleteUser } from "./redux/features/userSlice";
import { RootState, AppDispatch } from "./store";

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
    <div>
      <h1>Liste des utilisateurs</h1>
      {status === "loading" && <p>Chargement...</p>}
      {status === "failed" && <p>Erreur : {error}</p>}
      <button onClick={handleAddUser}>Ajouter un utilisateur</button>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.username} - {user.email}{" "}
            <button onClick={() => handleDeleteUser(user.id!)}>Supprimer</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;