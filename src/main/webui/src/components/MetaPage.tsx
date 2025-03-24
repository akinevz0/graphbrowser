import useApi from "../useApi"

const apiCount = "/api/count"

const MetaPageComponent = () => {
    const countResponse = useApi(apiCount)

    return (
        <>
            {`there are currently ${countResponse} pages cached.`}
        </>
    )
}

export default MetaPageComponent