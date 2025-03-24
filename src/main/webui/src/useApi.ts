import { useEffect, useState } from "react"

const useApi = (api: string): string | null => {
    const [state, setState] = useState<string | null>(null)
    useEffect(() => {
        (async () => {
            if (!api) return null;
            if (state) return state;
            console.log("fetching", api);
            const response = await fetch(api)
            const text = await response.text()
            setState(text)
        })()
    }, [])
    return state
}

export default useApi