import useApi from "../useApi"
import { useLocation } from "react-router-dom"
import UrlInput from "./UrlInput"

const apiGet = "/api"

const ViewPageComponent = () => {

    const { search } = useLocation()

    const apiGetResponse = useApi(`${apiGet}${search}`)

    return (
        <>
            <UrlInput />
            <br />
            {/* <pre>{JSON.stringify(apiGetResponse, null, 2)}</pre> */}
            <pre>{apiGetResponse}</pre>
        </>
    )
}

export default ViewPageComponent