import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";

// Type pour un utilisateur
interface User {
  id?: string;
  username: string;
  email: string;
  password: string;
  role: string;
}

// Type pour l'état Redux
interface UserState {
  users: User[];
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: UserState = {
  users: [],
  status: "idle",
  error: null,
};

// 🚀 Action asynchrone pour récupérer les utilisateurs
export const fetchUsers = createAsyncThunk("users/fetchUsers", async () => {
  const response = await axios.get<User[]>("http://localhost:8080/users");
  return response.data;
});

// 🚀 Action asynchrone pour créer un utilisateur
export const createUser = createAsyncThunk(
  "users/createUser",
  async (userData: User) => {
    const response = await axios.post<User>(
      "http://localhost:8080/users",
      userData
    );
    return response.data;
  }
);

// 🚀 Action asynchrone pour supprimer un utilisateur
export const deleteUser = createAsyncThunk(
  "users/deleteUser",
  async (id: string) => {
    await axios.delete(`http://localhost:8080/users/${id}`);
    return id;
  }
);

// 📌 Slice Redux
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {}, // Pas de reducers classiques, tout est async
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchUsers.fulfilled, (state, action: PayloadAction<User[]>) => {
        state.status = "succeeded";
        state.users = action.payload;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message ?? "Erreur inconnue";
      })
      .addCase(createUser.fulfilled, (state, action: PayloadAction<User>) => {
        state.users.push(action.payload);
      })
      .addCase(deleteUser.fulfilled, (state, action: PayloadAction<string>) => {
        state.users = state.users.filter((user) => user.id !== action.payload);
      });
  },
});

export default userSlice.reducer;