import React from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../store";
import { logout } from "../../redux/features/userSlice";
import "./Navbar.css";

const Navbar: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const user = useSelector((state: RootState) => state.auth.user);

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <nav className="navbar">
      <div className="navbar-title">
        <Link to="/">MonApp</Link>
      </div>
      <ul className="navbar-links">
        <li>
          <Link to="/" className="navbar-link">Accueil</Link>
        </li>
        <li>
          <Link to="/users" className="navbar-link">Utilisateurs</Link>
        </li>
        {user ? (
          <li>
            <button onClick={handleLogout} className="navbar-button">
              Déconnexion
            </button>
          </li>
        ) : (
          <li>
            <Link to="/login" className="navbar-button">Connexion</Link>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
