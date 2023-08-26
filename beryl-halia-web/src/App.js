import React, {useState, useCallback} from "react";
import Gallery from "react-photo-gallery";
import Carousel, {Modal, ModalGateway} from "react-images";
import {photos} from "./photos";

function App() {
    const [currentImage, setCurrentImage] = useState(0);
    const [viewerIsOpen, setViewerIsOpen] = useState(false);

    const openLightbox = useCallback((event, {photo, index}) => {
        setCurrentImage(index);
        setViewerIsOpen(true);
    }, []);

    const closeLightbox = () => {
        setCurrentImage(0);
        setViewerIsOpen(false);
    };

    return (
        <div className={"container"}>
            <div>
                <h1 className={"text"}>
                    Beryl Halia Ou Hinze
                </h1>
                <h2 className={"text"}>
                    August 23, 2023
                </h2>
                <Gallery photos={photos} onClick={openLightbox} margin={4}/>
                <ModalGateway>
                    {viewerIsOpen ? (
                        <Modal onClose={closeLightbox}>
                            <Carousel
                                currentIndex={currentImage}
                                views={photos.map(x => ({
                                    ...x,
                                    srcset: x.srcSet,
                                    caption: x.title
                                }))}
                            />
                        </Modal>
                    ) : null}
                </ModalGateway>
                <a href={"content/beryl-all-images.zip"} className={"text"}>
                    <h4>download all high resolution as zip file</h4>
                </a>
            </div>
        </div>
    );
}

export default App