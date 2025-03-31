import { useApi } from "../useApi"

const apiCount = "/api/count"
const apiAll = "/api/all"

const MetaPageComponent = () => {
    const { text: tCount, response: rCount } = useApi(apiCount)
    const { text: tAll, response: rAll } = useApi(apiAll)

    return (
        <>
            {rCount?.ok && `there are currently ${tCount} pages cached.`}
            {rAll?.ok && <pre> {
                JSON.stringify(JSON.parse(tAll ?? ""), null, 2)
            }</pre>}
        </>
    )

}

export default MetaPageComponent