import { useApi } from "../useApi"
import { useLocation } from "react-router-dom"
import UrlInput from "./UrlInput"
import type { FC } from "react"
import { ScrollArea } from "@base-ui-components/react"
import { useErrorBoundary } from "react-error-boundary"

const apiView = "/api/view"

const DataTable: FC<{ json: string }> = ({ json }) => {
    return <ScrollArea.Root className="h-full">
        <ScrollArea.Viewport >
            <pre>
                {JSON.stringify(JSON.parse(json), null, 4)}
            </pre>
        </ScrollArea.Viewport>
        <ScrollArea.Scrollbar >
            <ScrollArea.Thumb />
        </ScrollArea.Scrollbar>
        <ScrollArea.Corner />
    </ScrollArea.Root>
}

const ViewPageComponent = () => {
    const { showBoundary } = useErrorBoundary()
    const { search } = useLocation()
    const url = search.substring(1 + search.indexOf('='))

    const { response, loading, text } = useApi(search && `${apiView}${search}`)
    console.dir(response)
    console.dir(loading)
    console.dir(text)
    if (response && !response.ok) {
        showBoundary(new Error(text));
    }
    return (
        <>
            <UrlInput current={url} />
            <br />
            {/* <pre>{JSON.stringify(apiGetResponse, null, 2)}</pre> */}
            {(response && response.ok && text) && <DataTable json={text} />}
        </>
    )
}

export default ViewPageComponent