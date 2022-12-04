import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import {EMain} from './eiffel/Main'
import {ELogin} from './eiffel/Login'
import {GLogin,GRegister} from './gustave/Login'
import {GPayment} from './gustave/Payment'
import {EProfile} from './eiffel/Profile'
import {GPanier} from './gustave/Panier'
import {GProfile} from './gustave/Profile'
import {GMain} from './gustave/Main'
import {Main} from './Main'

const router = createBrowserRouter([
    {path: "/", element: <Main/>},
    {path: "/eiffel/", element: <EMain/>},
    {path: "/eiffel/login", element: <ELogin/>},
    {path: "/eiffel/profile", element: <EProfile/>},
    {path: "/gustave/", element: <GMain/>},
    {path: "/gustave/login", element: <GLogin/>},
    {path: "/gustave/register", element: <GRegister/>},
    {path: "/gustave/profile", element: <GProfile/>},
    {path: "/gustave/pay", element: <GPayment/>},
    {path: "/gustave/panier", element: <GPanier/>}

]);

const root = createRoot(document.getElementById("root"));
root.render(
    <RouterProvider router={router}></RouterProvider>
);
