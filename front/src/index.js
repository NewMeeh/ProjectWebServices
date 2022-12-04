import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import {Main} from './Main'
import {Login, Register} from './Login'
import {Payment} from './Payment'
import {Profile} from './Profile'
import {Panier} from './Panier'

export function getToken(){
    const token = localStorage.getItem('token');
    if (token != null) {
        return true;
    }
    return false;
}

const router = createBrowserRouter([
    {path: "/", element: <Main/>,},
    {path: "/login", element: <Login/>},
    {path: "/register", element: <Register/>},
    {path: "/pay", element: <Payment/>},
    {path: "/profile", element: <Profile/>},
    {path: "/panier", element: <Panier/>}
]);

const root = createRoot(document.getElementById("root"));
root.render(
    <RouterProvider router={router}></RouterProvider>
);
