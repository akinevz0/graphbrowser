import MetaPageComponent from "./components/MetaPage";
import HomePageComponent from "./components/HomePage";
import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";

export interface Page {
    path: string
    label: string
    element: React.ComponentType
}

const page = (path: string, label: string, element: React.ComponentType): Page => ({
    path,
    label,
    element: () => <main><section>{React.createElement(element)}</section></main>,
})


const pages: Page[] = [
    page("/", "Home", HomePageComponent),
    page("/meta", "Metadata Browser", MetaPageComponent),
]

export const AppRoutes = () => {
    return <BrowserRouter>
        <Header links={
            pages
        } />
        <Routes>
            {
                pages.map((page, index) => (
                    <Route key={index} path={page.path} Component={page.element} />
                ))
            }
        </Routes>
    </BrowserRouter>

}