import { useLocation } from "react-router-dom"
import UrlInput from "./UrlInput"
import { useErrorBoundary } from "react-error-boundary"
import { Loading } from "./Loading"
import { DataTable } from "./DataTable"
import type { FC, ReactNode } from "react"
import { useQuery } from "@tanstack/react-query"

const apiView = "/api/view"

const fetchView = (current:string) => fetch(`${apiView}?q=${current}`).then((res) => res.text())

const doNotFetch = () => Promise.resolve("Waiting for a valid URL")

const DatabaseViewFetch: FC<{ current: string, children: (text: string) => ReactNode }> = ({ children, current }) => {
    // validate the URL
    let isValidURL: boolean = false;
    let isNullURL: boolean = false;
    try {
        if (!current) isNullURL = true
        if (current && current.trim().length === 0) isNullURL = true
        new URL(current)
        isValidURL = true;
        // eslint-disable-next-line @typescript-eslint/no-unused-vars, no-empty
    } catch (error) { }

    const { showBoundary } = useErrorBoundary()
    const { isPending, error, data } = useQuery({
        queryKey: [apiView],
        queryFn: (isValidURL && !isNullURL) ? ()=>fetchView(current) : doNotFetch
    })
    if (isNullURL) return <></>
    if (isPending) return <Loading />
    if (error) showBoundary(error)
    if (isValidURL) return children(data ?? "")
    showBoundary(new Error("Query string must be a valid URL"))
}
const ViewPageComponent = () => {
    const { search } = useLocation()
    const current = search.substring(1 + search.indexOf('='))
    const queryUrl = decodeURIComponent(current)
    console.log("decoded", queryUrl)
    return <>
            <UrlInput current={queryUrl} />
            <br />
            <DatabaseViewFetch current={queryUrl}>
                {(text) => (<DataTable json={text} />)}
            </DatabaseViewFetch>
        </>
    
}

export default ViewPageComponent