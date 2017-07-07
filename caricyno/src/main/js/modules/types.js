type NewsPost = {
    id: String,
    title: String,
    category: String,
    mainPhoto: String,
    htmlText: String,
    tags: String,
    visibility: String,
    commentsRights: String
}

type OpenGraph = {
    index: number,
    href: String,
    title: String,
    photo: String,
    description: String
}

export const NewsPostObject: NewsPost = {};
export const OpenGraphObject: OpenGraph = {};