import { useEffect, useState } from "react";

export const useApi = (api: string) => {
    // Instead of using [state, setState] directly, use a separate variable to track if data is still loading
    const [response, setResponse] = useState<Response | null>(null);
    const [text, setText] = useState<string | null>(null);
    const [completed, setCompleted] = useState(false);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        // Only trigger the API call when api changes and data is not currently loading
        (async () => {
            if (!api) return;
            if (loading) return; // Prevent re-triggering due to pending response from previous call
            if (completed) return;
            setLoading(true); // Set loading state before making the request
            try {
                console.log("fetching", api);
                const response = await fetch(api)
                const text = await response.text()
                setResponse(response)
                setText(text)
            } finally {
                setLoading(false); // Reset loading state regardless of success or failure
                setCompleted(true);
            }
        })()
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [api]);

    return { response: response ?? undefined, loading, text: text?? undefined };
}